package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.entity.BlockManager;
import com.mygdx.game.entity.Player;

public class GameSceneLvl2 extends GameScene {
    private EntityManager entityManager;
    private KeyStrokeManager keyStrokeManager;
    private BlockManager blockManager;
    private Player player;

    public GameSceneLvl2(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
        super(sceneManager, entityManager, keyStrokeManager, GameSceneType.GAME_SCENE_LVL2.getValue());
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.blockManager = new BlockManager();
    }

    @Override
    protected void createEntities() {
        this.player = new Player(
                GameConfig.PLAYER_START_POSITION.x,
                GameConfig.PLAYER_START_POSITION.y,
                GameConfig.PLAYER_WIDTH,
                GameConfig.PLAYER_HEIGHT,
                GameConfig.PLAYER_SPEED,
                Assets.PLAYER.getFileName(),
                GameEntityType.PLAYER.getValue(),
                keyStrokeManager, entityManager);

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

        // randomly spawn the burgers
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_BURGERS,
                entityManager.getAllEntityPosition(), Assets.BURGER.getFileName(), GameEntityType.BURGER.getValue()));

        // randomly spawn the carrots
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_CARROTS,
                entityManager.getAllEntityPosition(), Assets.CARROT.getFileName(), GameEntityType.CARROT.getValue()));
    }

    @Override
    protected void checkWinCondition(Entity entity) {
        if (entityManager.getEntities(GameEntityType.APPLE.getValue()).size() == 0 ||
                entityManager.getEntities(GameEntityType.CARROT.getValue()).size() == 0) {
            for (Entity exitPortal : entityManager.getEntities(GameEntityType.EXIT_PORTAL.getValue())) {
                if (!exitPortal.isVisible()) {
                    exitPortal.setVisible(true);
                } else if (CollisionManager.isCollidingWith(entity, exitPortal)) {
                    setNextScene(GameSceneType.GAME_OVER_WIN.getValue());
                }
            }
        }
    }

    @Override
    protected void applyEffects() {
        if (player.isCarrotEffectActive()) {
            getTimer().addTime(GameConfig.CARROT_EFFECT_TIME);
            player.setCarrotEffectActive(false);
        }
    }

}
