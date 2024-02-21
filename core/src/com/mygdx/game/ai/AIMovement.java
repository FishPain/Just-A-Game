package com.mygdx.game.ai;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.controls.ControlManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig;
import com.mygdx.game.collision.Collision;

public class AIMovement extends Collision {
    private AIBot aibot;

    public AIMovement (float x, float speed, boolean isJumping, float jumpSpeed,
    float gravity) {

    }
    public void AImovement(Entity entity, ArrayList<Entity> allEntities,
            ArrayList<Vector2> bodyPositions, float deltaTime) {
                Vector2 movementDelta = new Vector2();
                movementDelta = ControlManager.calculateMovement(movementDelta, GameConfig.ENTITY_SPEED * deltaTime, 0);
                // Use the CollisionManager for collision checks
                boolean horizontalCollision = checkHorizontalCollision(entity, movementDelta,
                allEntities, bodyPositions);

                if (!horizontalCollision){
                    aibot.autonomousUpdate();
                }
    }

    
}
