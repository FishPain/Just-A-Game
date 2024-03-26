package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.engine.collision.CollisionManager;
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
import com.mygdx.game.entity.BlockManager;
import com.mygdx.game.entity.Player;
import java.util.ArrayList;

public class GameSceneLvl2 extends Scene {
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private KeyStrokeManager keyStrokeManager;
    private BlockManager blockManager;
    private ButtonManager buttonManager;
    private Timer timer;
    private Player player;
    private String nextScene;
    private boolean isPaused;
    private boolean pauseKeyIsPressed;
    private Texture pauseOverlay;
    private Button resumeBtn;
    private Button mainMenuBtn;
    private Button quitBtn;

    public GameSceneLvl2(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
        super(Assets.GAME_SCENE_BG.getFileName(),
                Assets.GAME_SCENE_SOUND.getFileName(),
                GameSceneType.GAME_SCENE_LVL2.getValue());
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.blockManager = new BlockManager();
        this.timer = new Timer(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT - 50,
                GameConfig.TIME_LIMIT);
        this.isPaused = false;
        this.pauseKeyIsPressed = false;
        this.nextScene = null;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
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

    @Override
    public void show() {

        isPaused = false; // game is not paused

        // spawn the player
        this.player = new Player(GameConfig.PLAYER_START_POSITION.x, GameConfig.PLAYER_START_POSITION.y,
                GameConfig.PLAYER_SIZE,
                GameConfig.PLAYER_SIZE,
                GameConfig.PLAYER_SPEED, Assets.PLAYER_HEAD.getFileName(), Assets.PLAYER_BODY.getFileName(),
                GameEntityType.PLAYER_HEAD.getValue(), keyStrokeManager, entityManager);

        entityManager.addEntity(player);
        // spawn the block borders
        entityManager.addEntities(blockManager.createBlocks(GameConfig.BLOCK_BORDER_POSITIONS));

        // spawn exit portal
        entityManager.addEntity(blockManager.createExitPortal(GameConfig.EXIT_PORTAL_POSITION));

        // randomly spawn the blocks obstacles
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_OBSTACLES,
                entityManager.getAllEntityPosition(), Assets.BLOCK.getFileName(), GameEntityType.BLOCK.getValue()));

        // randomly spawn the apples
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_APPLES,
                entityManager.getAllEntityPosition(), Assets.APPLE.getFileName(), GameEntityType.APPLE.getValue()));

        // randomly spawn the carrots
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_CARROTS,
                entityManager.getAllEntityPosition(), Assets.CARROT.getFileName(), GameEntityType.CARROT.getValue()));

        // randomly spawn the burgers
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_BURGERS,
                entityManager.getAllEntityPosition(), Assets.BURGER.getFileName(), GameEntityType.BURGER.getValue()));

        // Initialize pause overlay texture
        pauseOverlay = new Texture(Gdx.files.internal(Assets.PAUSE_OVERLAY_BG.getFileName()));

        // Define positions and dimensions for buttons
        float buttonSpacing = 50;
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float buttonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2;
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

        timer.startTimer();
        if (GameConfig.IS_MUSIC_ENABLED)
            playBackgroundMusic(GameConfig.MUSIC_VOLUME);
    }

    @Override
    public void hide() {
        this.nextScene = null;
        timer.resetTimer();
        entityManager.dispose();
        if (GameConfig.IS_MUSIC_ENABLED) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void render(float delta) {
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        // update timer
        timer.updateAndRender(batch);
        if (timer.getRemainingTime() <= 0) {
            nextScene = GameSceneType.GAME_OVER_LOSE.getValue();
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


        // draw and move the entities
        ArrayList<Entity> entities = entityManager.getEntities();
        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entityManager.getAllCollidableEntity(), delta);

            // If the player has eaten all the apples, the game ends
            if (entityManager.getEntities(GameEntityType.APPLE.getValue()).size() == 0 ||
                    entityManager.getEntities(GameEntityType.CARROT.getValue()).size() == 0) {
                for (Entity exitPortal : entityManager.getEntities(GameEntityType.EXIT_PORTAL.getValue())) {
                    if (!exitPortal.isVisable()) {
                        exitPortal.setVisable(true);
                    } else if (CollisionManager.isCollidingWith(entity, exitPortal)) {
                        nextScene = GameSceneType.GAME_OVER_WIN.getValue();
                    }
                }
            }
        }

        // bulk remove entity to prevent concurrent modification
        entityManager.removeEntities();

        if (player.isCarrotEffectActive()) {
            timer.addTime(10);
            player.setCarrotEffectActive(false);
        }

        // set the next scene
        if (nextScene != null)
            sceneManager.setScene(nextScene);
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.pauseTimer();
            entityManager.setMovability(entityManager.getEntities(GameEntityType.PLAYER_HEAD.getValue()), false);
        } else {
            timer.resumeTimer();
            entityManager.setMovability(entityManager.getEntities(GameEntityType.PLAYER_HEAD.getValue()), true);
        }
    }

    @Override
    public void dispose() {
        pauseOverlay.dispose();
    }
}
