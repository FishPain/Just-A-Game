package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
<<<<<<< HEAD
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.collision.CollisionManager;
import com.badlogic.gdx.math.Rectangle;
=======
import com.badlogic.gdx.math.Rectangle;

import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.engine.collision.CollisionManager;

>>>>>>> 4cb3795079ba21f2f3ac0b0783fb1bd6e06f4e78
import java.util.ArrayList;

public class Snake extends Entity {

    public Snake(float x, float y, float width, float height, String texturePath, float speed, EntityType entityType) {
        super(x, y, width, height, texturePath, speed, true, entityType);
    }

    @Override
    public void move(ArrayList<Entity> allEntities) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 horizontalMovementDelta = calculateHorizontalMovement(deltaTime);
        Vector2 verticalMovementDelta = calculateVerticalMovement(deltaTime);

        // Check for horizontal movement possibility
        Vector2 newHorizontalPosition = new Vector2(this.x + horizontalMovementDelta.x, this.y);
        boolean horizontalCollision = CollisionManager.willCollide(this, newHorizontalPosition, allEntities);

        // Apply horizontal movement if no collision detected
        if (!horizontalCollision) {
            this.x = newHorizontalPosition.x;
        }

        // Apply vertical movement (gravity) if not on a platform
        boolean onPlatform = isOnPlatform(this, allEntities);
        if (!onPlatform) {
            Vector2 newVerticalPosition = new Vector2(this.x, this.y + verticalMovementDelta.y);
            boolean verticalCollision = CollisionManager.willCollide(this, newVerticalPosition, allEntities);
            if (!verticalCollision) {
                this.y = newVerticalPosition.y;
            }
        }

        updatePosition(); // Update the entity's rectangle for collision checks
    }

<<<<<<< HEAD
=======
    public boolean isReachEnd(ArrayList<Entity> entity) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 horizontalMovementDelta = calculateHorizontalMovement(deltaTime);
        Vector2 newHorizontalPosition = new Vector2(this.x + horizontalMovementDelta.x, this.y);
        boolean horizontalCollision = CollisionManager.willCollide(this, newHorizontalPosition, entity);
        if (horizontalCollision) {
            return true;
        }
        return false;
    }

>>>>>>> 4cb3795079ba21f2f3ac0b0783fb1bd6e06f4e78
    private Vector2 calculateHorizontalMovement(float deltaTime) {
        Vector2 movementDelta = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movementDelta.x -= speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movementDelta.x += speed * deltaTime;
        }
        return movementDelta;
    }

    private Vector2 calculateVerticalMovement(float deltaTime) {
        // This assumes gravity is constantly applied, you might adjust if you have
        // jumping logic
        return new Vector2(0, -GRAVITY * deltaTime);
    }

    private boolean isOnPlatform(Entity entity, ArrayList<Entity> allEntities) {
        // Extend your CollisionManager or add logic here to check if the entity is
        // standing on a platform
        // This could involve checking for a collision directly beneath the entity,
        // indicating it's supported
        for (Entity other : allEntities) {
<<<<<<< HEAD
            if (other != entity && other.getEntityType() == EntityType.PLATFORM) {
=======
            if (other != entity && other.getEntityType() == GameEntityType.PLATFORM) {
>>>>>>> 4cb3795079ba21f2f3ac0b0783fb1bd6e06f4e78
                Rectangle slightlyBelow = new Rectangle(entity.getRectangle());
                slightlyBelow.y -= 1; // Check just below the entity
                if (slightlyBelow.overlaps(other.getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

<<<<<<< HEAD
    // Modify the rendering of snake as a whole into a single entity
    public static void addSnakeToEntityManager(EntityManager entityManager, float x, float y, String texturePath, EntityType entityType) {
        entityManager.addEntity(new Snake(x, y, 50, 50, texturePath, 200, entityType));
    }

=======
>>>>>>> 4cb3795079ba21f2f3ac0b0783fb1bd6e06f4e78
}