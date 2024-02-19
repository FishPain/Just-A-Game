package com.mygdx.engine.controls;

import com.badlogic.gdx.math.Vector2;

public class ControlManager {

    public static Vector2 calculateMovement(Vector2 movementDelta, float x, float speed, float deltaTime) {
        movementDelta.x += speed * deltaTime;
        return movementDelta;
    }

    public Vector2 calculateVerticalMovement(float x, float gravity, float deltaTime) {
        return new Vector2(0, -gravity * deltaTime);
    }
}
