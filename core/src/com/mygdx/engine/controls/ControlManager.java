package com.mygdx.engine.controls;

import com.badlogic.gdx.math.Vector2;

public class ControlManager {
    public static Vector2 calculateMovement(Vector2 movementDelta, float x, float y) {
        movementDelta.x += x;
        movementDelta.y += y;
        return movementDelta;
    }
}
