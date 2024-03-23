package com.mygdx.game.collision;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.fonts.Font;
import com.mygdx.engine.io.Timer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.Player;

public class Collision extends CollisionManager {
    static private Timer timerApple = new Timer(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT - 50, 3);
    static private Timer timerCarrot = new Timer(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT - 100, 6);

    public static void collideEffect(GameEntityType entity, Player snake) {
        if (entity == GameEntityType.APPLE) {
            timerApple.startTimer();
            System.out.println("APPLE");
            snake.setSpeed(snake.getSpeed() * 2);
            System.out.println("snake.setSpeed: " + snake.getSpeed());
            snake.setAppleEffectActive(true);

        } else if (entity == GameEntityType.CARROT) {
            System.out.println("CARROT");
            timerCarrot.startTimer();
            snake.setSpeed(snake.getSpeed() * 4);
            System.out.println("snake.getSpeed(): " + snake.getSpeed());
            snake.setCarrotEffectActive(true);

        } else if (entity == GameEntityType.BURGER) {
            System.out.println("BURGER");
            snake.setSpeed(snake.getSpeed() / 1.5f);
            System.out.println("snake.getSpeed(): " + snake.getSpeed());

        } else if (entity == GameEntityType.BLOCK) {
            System.out.println("BLOCK");
            System.out.println("SPEED AT BLOCK: " + snake.getSpeed());
        }

        // Update timers
        if (snake.isAppleEffectActive() == true) {
            timerApple.updateEffect();
        }
        if (snake.isCarrotEffectActive() == true) {
            timerCarrot.updateEffect();
        }

        // Check if timers have expired and restore speed accordingly
        if (timerApple.getRemainingTime() <= 0) {
            snake.setSpeed(GameConfig.PLAYER_SPEED / 2); // Set speed back to original value

            snake.setAppleEffectActive(false); // Reset apple effect flag
        }
        if (timerCarrot.getRemainingTime() <= 0) {
            snake.setSpeed(GameConfig.PLAYER_SPEED / 3); // Set speed back to original value

            snake.setCarrotEffectActive(false); // Reset carrot effect flag
        }
    }

}
