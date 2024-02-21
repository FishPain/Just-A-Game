package com.mygdx.game.collision;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.movements.Movement;

public class Collision extends CollisionManager {

    public static boolean isOnPlatform(Entity entity, ArrayList<Entity> platforms, ArrayList<Vector2> bodyPositions) {
        // Check entity's position
        if (isEntityOnPlatform(entity, platforms))
            return true;

        // Check body positions
        return bodyPositions.stream().anyMatch(bodyPosition -> isBodyPositionOnPlatform(bodyPosition, platforms));
    }

    private static boolean isEntityOnPlatform(Entity entity, ArrayList<Entity> platforms) {
        Rectangle slightlyBelow = new Rectangle(entity.getRectangle());
        slightlyBelow.y -= 1; // Check just below the entity
        for (Entity platform : platforms) {
            if (platform.getEntityType() == GameEntityType.PLATFORM
                    && slightlyBelow.overlaps(platform.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBodyPositionOnPlatform(Vector2 bodyPosition, ArrayList<Entity> platforms) {
        Rectangle bodyRect = new Rectangle(bodyPosition.x, bodyPosition.y, 1, 1);
        bodyRect.y -= 1;
        for (Entity platform : platforms) {
            if (platform.getEntityType() == GameEntityType.PLATFORM && bodyRect.overlaps(platform.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    // A helper method to check horizontal collisions for both head and body
    // segments
    public static boolean checkHorizontalCollision(Entity entity, Vector2 horizontalMovementDelta,
            ArrayList<Entity> allEntities, ArrayList<Vector2> bodyPositions) {
        // Check collision for the entity's head
        if (willCollide(entity, new Vector2(entity.getX() + horizontalMovementDelta.x, entity.getY()), allEntities)) {
            return true;
        }
        for (Vector2 body : bodyPositions) {
            if (willCollide(body, new Vector2(body.x + horizontalMovementDelta.x, body.y), allEntities)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isReachEnd(Entity entity, ArrayList<Entity> allEntities, Movement movement) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 horizontalMovementDelta = movement.calculateHorizontalMovement(entity.getX(), entity.getSpeed(),
                deltaTime);
        Vector2 newHorizontalPosition = new Vector2(entity.getX() + horizontalMovementDelta.x, entity.getY());

        Vector2 verticalMovementDelta = movement.calculateVerticalMovement(entity.getY(), entity.getSpeed(), deltaTime);
        Vector2 newVerticalPosition = new Vector2(entity.getX(), entity.getY() + verticalMovementDelta.y);

        return willCollide(entity, newHorizontalPosition, allEntities)
                || willCollide(entity, newVerticalPosition, allEntities);
    }

}
