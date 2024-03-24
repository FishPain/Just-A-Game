package com.mygdx.game.collision;

import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.Player;

public class GameCollision extends CollisionManager {
    static private Timer timerApple = new Timer(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT - 50, 3);
    static private Timer timerBurger = new Timer(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT - 50, 3);

    public static void collideEffect(Entity collidedEntity, Player player) {
        String entityType = collidedEntity.getEntityType();

        // Update timers
        if (player.isAppleEffectActive()) {
            timerApple.updateEffect();
            if (timerApple.getRemainingTime() <= 0) {
                player.setSpeed(GameConfig.PLAYER_SPEED); // Set speed back to original value
                player.setAppleEffectActive(false); // Reset apple effect flag
                timerApple.resetTimer();
            }
        }
        // Update timers
        if (player.isBurgerEffectActive()) {
            timerBurger.updateEffect();
            if (timerBurger.getRemainingTime() <= 0) {
                player.setSpeed(GameConfig.PLAYER_SPEED); // Set speed back to original value
                player.setBurgerEffectActive(false); // Reset apple effect flag
                timerBurger.resetTimer();
            }
        }

        // Apple increase speed by 1.5 times
        if (GameEntityType.fromValue(entityType) == GameEntityType.APPLE) {
            collidedEntity.setToRemove(true);
            player.setSpeed(player.getSpeed() * 1.5f);
            player.setAppleEffectActive(true);
            timerApple.startTimer();
        }
        // burger slow down the player by 1.5 times
        else if (GameEntityType.fromValue(entityType) == GameEntityType.BURGER) {
            collidedEntity.setToRemove(true);
            player.setSpeed(player.getSpeed() / 1.5f);
            player.setBurgerEffectActive(true);
            timerBurger.startTimer();
        }
        // Carrot increase time by 10 seconds
        else if (GameEntityType.fromValue(entityType) == GameEntityType.CARROT) {
            collidedEntity.setToRemove(true);
            player.setCarrotEffectActive(true);
        }
    }

}
