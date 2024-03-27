package com.mygdx.engine.ai;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.controls.ControlManager;

public class MovementAI extends CollisionManager {

    public Vector2 calculateAIHorizontalMovement(float speed,
            float deltaTime) {
        Vector2 movementDelta = new Vector2();
        movementDelta = ControlManager.calculateMovement(movementDelta, speed * deltaTime, 0);
        return movementDelta;
    }

    public Vector2 calculateVerticalMovement(float speed,
            float deltaTime) {
        Vector2 movementDelta = new Vector2();
        movementDelta = ControlManager.calculateMovement(movementDelta, 0, speed * deltaTime);
        return movementDelta;
    }

}
