package com.mygdx.game.entity;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;

import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.collision.CollisionManager;

import com.mygdx.game.movements.Movement;
import com.mygdx.game.movements.Movement.Direction;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.ai.AIMovement;

public class AIPlayer extends Entity {
    private Texture texture;
    private AIMovement aiMovement;
    private EntityManager entityManager;
    private AIPlayer aiPlayer;
    private Direction currentDirection;
    private int points;

    public AIPlayer(float x, float y, float width, float height, String texturePath, float speed, String entityType,
            KeyStrokeManager keyStrokeManager,
            EntityManager entityManager) {
        super(x, y, width, height, texturePath, speed, true, entityType, true, false);

        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.aiMovement = new AIMovement(entityManager, aiPlayer);
        this.entityManager = entityManager;
        this.currentDirection = Movement.Direction.RIGHT;
        this.points = 0;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }

    public void moveLeft() {
        currentDirection = Movement.Direction.LEFT;
        moveHorizontally(-15);
    }

    public void moveRight() {
        currentDirection = Movement.Direction.RIGHT;
        moveHorizontally(15);
    }

    public void moveUp() {
        currentDirection = Movement.Direction.UP;
        moveVertically(15);
    }

    public void moveDown() {
        currentDirection = Movement.Direction.DOWN;
        moveVertically(-15);
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    private void moveHorizontally(float deltaX) {
        Vector2 newHorizontalPosition = new Vector2(this.x + deltaX, this.y);
        // Apply movement if no collision
        this.x = newHorizontalPosition.x;
        // REQUIRES GRAPHICS TO UPDATE THE SNAKE POSITION ON SCREEN
        updatePosition();
    }

    private void moveVertically(float deltaY) {
        Vector2 newVerticalPosition = new Vector2(this.x, this.y + deltaY);
        // Apply movement if no collision
        this.y = newVerticalPosition.y;
        updatePosition();
    }

    @Override
    public void move(ArrayList<Entity> allEntities, float deltaTime) {
        aiMovement.retrieveAppleAndCarrotPos();
        ArrayList<Point> appleAndCarrotPos = aiMovement.getAppleAndCarrotPos();
        if (!appleAndCarrotPos.isEmpty()) { // Keep moving as long as there are apples
            if (this.isMovable) {
                // Apply movements
                aiMovement.applyVerticalMovement(this, allEntities, deltaTime);
                aiMovement.applyHorizontalMovement(this, allEntities, deltaTime);
            }
            updatePosition();
        }

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
