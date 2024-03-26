package com.mygdx.game.collision;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.Player;

public class GameCollision extends CollisionManager {
    static private Timer timerApple = new Timer(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT - 50,
            GameConfig.APPLE_EFFECT_TIME);
    static private Timer timerBurger = new Timer(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT - 50,
            GameConfig.BURGER_EFFECT_TIME);
    static private String entityType;

    public static void collideEffect(Entity collidedEntity, Player player) {
        if (collidedEntity != null) {
            entityType = collidedEntity.getEntityType();
        }

        // reset the speed if the effect is over
        if (player.isAppleEffectActive()) {
            timerApple.updateEffect();
            if (timerApple.isTimerEnded()) {
                player.setSpeed(GameConfig.PLAYER_SPEED); // Set speed back to original value
                player.setAppleEffectActive(false); // Reset apple effect flag
                timerApple.resetTimer();
            }
        }

        // reset the speed if the effect is over
        if (player.isBurgerEffectActive()) {
            timerBurger.updateEffect();
            if (timerBurger.isTimerEnded()) {
                player.setSpeed(GameConfig.PLAYER_SPEED); // Set speed back to original value
                player.setBurgerEffectActive(false); // Reset apple effect flag
                timerBurger.resetTimer();
            }
        }

        if (collidedEntity == null) {
            return;
        }
        // Apple increase speed by 1.5 times
        else if (GameEntityType.fromValue(entityType) == GameEntityType.APPLE) {
            collidedEntity.setToRemove(true);
            player.setSpeed(player.getSpeed() * GameConfig.APPLE_SPEED_MULTIPLIER);
            player.setAppleEffectActive(true);
            timerApple.startTimer();
        }
        // burger slow down the player by 1.5 times
        else if (GameEntityType.fromValue(entityType) == GameEntityType.BURGER) {
            collidedEntity.setToRemove(true);
            player.setSpeed(player.getSpeed() / GameConfig.BURGER_SPEED_MULTIPLIER);
            player.setBurgerEffectActive(true);
            timerBurger.startTimer();
        }
        // Carrot increase time by 10 seconds
        else if (GameEntityType.fromValue(entityType) == GameEntityType.CARROT) {
            collidedEntity.setToRemove(true);
            player.setCarrotEffectActive(true);
        }

        // burger slow down the player by 1.5 times
        else if (GameEntityType.fromValue(entityType) == GameEntityType.BURGER) {
            collidedEntity.setToRemove(true);
            player.setSpeed(player.getSpeed() / 1.5f);
        }
    }


    public static boolean isEntityOnBlock(Entity entity, ArrayList<Entity> blocks) {
        Rectangle slightlyBelow = new Rectangle(entity.getRectangle());
        slightlyBelow.y -= 1; // Check just below the entity
        for (Entity block : blocks) {
            if (block.getEntityType() == GameEntityType.BLOCK.getValue()
                    && slightlyBelow.overlaps(block.getRectangle())) {
                return true;
            }
        }
        return false;
    }
}
