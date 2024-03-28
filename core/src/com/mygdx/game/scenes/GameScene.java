package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameButtonType;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.engine.io.button.Button;
import com.mygdx.engine.io.button.ButtonClickListener;
import com.mygdx.engine.io.button.ButtonManager;
import java.util.ArrayList;

public abstract class GameScene extends Scene {
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private KeyStrokeManager keyStrokeManager;
    private ButtonManager buttonManager;
    private Timer timer;
    private String nextScene;
    private boolean isPaused;
    private boolean pauseKeyIsPressed;
    private Texture pauseOverlay;
    private Button resumeBtn;
    private Button mainMenuBtn;
    private Button quitBtn;
    private ButtonClickListener clickListener;

    public GameScene(
            SceneManager sceneManager,
            EntityManager entityManager,
            KeyStrokeManager keyStrokeManager,
            String gameSceneType, int timeLimit) {
        super(Assets.GAME_SCENE_BG.getFileName(),
                Assets.GAME_SCENE_SOUND.getFileName(), gameSceneType);
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.timer = new Timer(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT - 50,
                timeLimit);
        this.isPaused = false;
        this.pauseKeyIsPressed = false;
        this.nextScene = null;
        this.clickListener = null;
    }

    @Override
    public void show() {
        isPaused = false;
        clickListener = createButtonClickListener();
        createEntities();
        createPauseGameOverlay();

        timer.startTimer();
        if (GameConfig.IS_MUSIC_ENABLED)
            playBackgroundMusic(GameConfig.MUSIC_VOLUME);
    }

    @Override
    public void hide() {
        setNextScene(null);
        timer.resetTimer();
        entityManager.dispose();
        if (GameConfig.IS_MUSIC_ENABLED) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void render(float delta) {
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        timer.updateAndRender(batch);
        if (timer.isTimerEnded()) {
            this.nextScene = GameSceneType.GAME_OVER_LOSE.getValue();
        }

        // press esc key to pause the game and resume the game
        if (keyStrokeManager.isKeyPressed(GameConfig.Keystroke.PAUSE_RESUME.getKeystrokeName())) {
            if (!pauseKeyIsPressed) {
                togglePause();
                pauseKeyIsPressed = true;
            }
        } else {
            pauseKeyIsPressed = false;
        }

        if (isPaused) {
            batch.draw(pauseOverlay, 0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
            buttonManager.drawButtons(batch);
        }

        applyEffects();

        drawEntitiesAndCheckWinLoseCondition(delta);

        // bulk remove entity to prevent concurrent modification
        entityManager.removeEntities();

        // set the next scene
        if (nextScene != null)
            sceneManager.setScene(nextScene);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (buttonManager != null)
            buttonManager.dispose();
        if (pauseOverlay != null)
            pauseOverlay.dispose();
    }

    protected abstract void createEntities();

    protected abstract void applyEffects();

    protected abstract void checkWinCondition(Entity entity);

    protected abstract void checkLoseCondition();

    protected Timer getTimer() {
        return timer;
    }

    private void createPauseGameOverlay() {
        // Initialize pause overlay texture
        pauseOverlay = new Texture(Gdx.files.internal(Assets.PAUSE_OVERLAY_BG.getFileName()));

        // Define positions and dimensions for buttons
        float buttonSpacing = 50;
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float offsetBelowCenter = GameConfig.SCREEN_HEIGHT * 0.2f;
        float buttonY = (GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2) - offsetBelowCenter;        
        float resumeBtnX = startX;
        float mainMenuBtnX = startX + buttonWidth + buttonSpacing;
        float quitBtnX = mainMenuBtnX + buttonWidth + buttonSpacing;

        // Initialize and setup buttons
        resumeBtn = new Button(resumeBtnX, buttonY, buttonWidth, buttonHeight, GameButtonType.RESUME.getValue(),
                Assets.BUTTON_BG.getFileName(), GameConfig.GameButtonText.RESUME_BTN.getText(),
                GameConfig.Assets.FONT_PATH.getFileName(), GameConfig.BUTTON_FONT_SIZE);

        mainMenuBtn = new Button(mainMenuBtnX, buttonY, buttonWidth, buttonHeight,
                GameButtonType.MAIN_MENU.getValue(), Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.MAIN_MENU_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

        quitBtn = new Button(quitBtnX, buttonY, buttonWidth,
                buttonHeight, GameButtonType.QUIT.getValue(), Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.QUIT_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

        // Register the buttons
        buttonManager = new ButtonManager(clickListener);
        buttonManager.addButton(resumeBtn);
        buttonManager.addButton(mainMenuBtn);
        buttonManager.addButton(quitBtn);

        // Set input processor for buttons
        buttonManager.setButtonsInputProcessor();
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.pauseTimer();
            entityManager.setMovability(entityManager.getEntities(GameEntityType.PLAYER.getValue()), false);
            entityManager.setMovability(entityManager.getEntities(GameEntityType.AI_PLAYER.getValue()), false);
        } else {
            timer.resumeTimer();
            entityManager.setMovability(entityManager.getEntities(GameEntityType.PLAYER.getValue()), true);
            entityManager.setMovability(entityManager.getEntities(GameEntityType.AI_PLAYER.getValue()), true);
        }
    }

    private void drawEntitiesAndCheckWinLoseCondition(float delta) {
        ArrayList<Entity> entities = entityManager.getEntities();
        for (Entity entity : entities) {
            if (!entity.getEntityType().equals(GameEntityType.EXIT_PORTAL.getValue())) {
                entity.setVisible(!isPaused);
            }
            entity.draw(batch);
            entity.move(entityManager.getAllCollidableEntity(), delta);
            checkWinCondition(entity);
            checkLoseCondition();
        }
    }

    private ButtonClickListener createButtonClickListener() {
        return new ButtonClickListener() {
            @Override
            public void onClick(Button button) {
                GameButtonType btnType = GameButtonType.fromValue(button.getButtonType());
                if (btnType.equals(GameButtonType.RESUME)) {
                    togglePause();
                } else if (btnType.equals(GameButtonType.MAIN_MENU)) {
                    sceneManager.setScene(GameSceneType.MAIN_MENU.getValue());
                } else if (btnType.equals(GameButtonType.QUIT)) {
                    Gdx.app.exit();
                }
            }
        };
    }

    protected void setNextScene(String nextScene) {
        this.nextScene = nextScene;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public KeyStrokeManager getKeyStrokeManager() {
        return keyStrokeManager;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }

}
