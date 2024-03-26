package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.collision.CollisionManager;

import com.mygdx.game.movements.Movement;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;

import java.util.ArrayList;

public class Player extends Entity {
    private Texture texture;
    private Movement movement;
    private EntityManager entityManager;
    private boolean isAppleEffectActive;
    private boolean isCarrotEffectActive;
    private boolean isBurgerEffectActive;

    public Player(float x, float y, float width, float height, float speed, String texturePath, String entityType,
            KeyStrokeManager keyStrokeManager,
            EntityManager entityManager) {
        super(x, y, width, height, texturePath, speed, true, entityType, true, false);
        this.entityManager = entityManager;
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.movement = new Movement(keyStrokeManager, x, speed, false, 0, GameConfig.GRAVITY);
        this.isAppleEffectActive = false;
        this.isCarrotEffectActive = false;
    }

    // EFFECT FLAGS
    public boolean isAppleEffectActive() {
        return isAppleEffectActive;
    }

    public void setAppleEffectActive(boolean active) {
        isAppleEffectActive = active;
    }

    public boolean isCarrotEffectActive() {
        return isCarrotEffectActive;
    }

    public void setCarrotEffectActive(boolean active) {
        isCarrotEffectActive = active;
    }

    public boolean isBurgerEffectActive() {
        return isBurgerEffectActive;
    }

    public void setBurgerEffectActive(boolean active) {
        isBurgerEffectActive = active;
    }
    // END OF EFFECT FLAG

    public void move(ArrayList<Entity> allEntities, float deltaTime) {
        if (this.isMovable) {
            movement.applyHorizontalMovement(this, allEntities, deltaTime);
            movement.applyVerticalMovement(this, allEntities, deltaTime);
            // movement.applyMovement(this, allEntities, bodyPositions, deltaTime);
        }
        updatePosition();
    }

    @Override
    public boolean isGameEnd() {
        // If the player has eaten all the apples, the game ends
        if (entityManager.getEntities(GameEntityType.APPLE.getValue()).size() == 0
                || entityManager.getEntities(GameEntityType.CARROT.getValue()).size() == 0) {
            for (Entity entity : entityManager.getEntities(GameEntityType.EXIT_PORTAL.getValue())) {
                if (!entity.isVisible()) {
                    entity.setVisible(true);
                } else if (CollisionManager.isCollidingWith(this, entity)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
