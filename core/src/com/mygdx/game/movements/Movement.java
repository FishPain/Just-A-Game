package com.mygdx.game.movements;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.controls.ControlManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.game.GameConfig.Keystroke;

public class Movement {
    public static Vector2 calculateHorizontalMovement(KeyStrokeManager keyStrokeManager, float x, float speed,
            float deltaTime) {
        Vector2 movementDelta = new Vector2();
        if (keyStrokeManager.isKeyPressed(Keystroke.LEFT.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, x, -speed, deltaTime);
        }
        if (keyStrokeManager.isKeyPressed(Keystroke.RIGHT.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, x, speed, deltaTime);
        }
        return movementDelta;
    }

    public static Vector2 calculateVerticalMovement(float x, float gravity, float deltaTime) {
        Vector2 movementDelta = new Vector2(x, gravity * deltaTime);
        return ControlManager.calculateMovement(movementDelta, gravity, deltaTime);
    }
}
