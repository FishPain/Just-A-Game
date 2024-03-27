package com.mygdx.game.collision;

import com.mygdx.engine.ai.MovementAI;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.*;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.AIPlayer;
import com.mygdx.game.entity.Player;

public class GameCollision extends CollisionManager {
    static MovementAI movementAI;
    private AIPlayer aiPlayer;

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

    // collision for AI
    public static void collideEffectAI(Entity collidedEntity, AIPlayer aiPlayer, String typeOfCollision) {
        String entityType = collidedEntity.getEntityType();
        ArrayList<Entity> allEntities;
        Entity entity;
        Vector2 newPosition;
        if (GameEntityType.fromValue(entityType) == GameEntityType.BLOCK) {
            if (typeOfCollision == "horizontal") {
                if (1 + (int) (Math.random() * ((2 - 1) + 1)) == 1) {
                    aiPlayer.moveUp();
                } else {
                    aiPlayer.moveDown();
                }

            } else if (typeOfCollision == "vertical") {
                if (1 + (int) (Math.random() * ((2 - 1) + 1)) == 1) {
                    aiPlayer.moveRight();
                } else {
                    aiPlayer.moveLeft();
                }
            }

            // willCollide(entity, newPosition, allEntities);

            // aiPlayer.setSpeed(aiPlayer.getSpeed() / 1.5f);
            // aiPlayer.moveRight();
            // aiPlayer.moveDown();

            // try {
            // // Sleep for 2 seconds
            // Thread.sleep(2000);
            // }

            int randNumber = 1 + (int) (Math.random() * ((4 - 1) + 1));

            // switch (randNumber) {
            // case 1:
            // System.out.println("CASE 1");
            // aiPlayer.moveLeft();
            // break;
            // case 2:
            // System.out.println("CASE 2");
            // aiPlayer.moveRight();
            // break;
            // case 3:
            // System.out.println("CASE 3");
            // aiPlayer.moveUp();

            // break;
            // case 4:
            // System.out.println("CASE 4");
            // aiPlayer.moveDown();

            // break;
            // default:
            // }

        }
        if (GameEntityType.fromValue(entityType) == GameEntityType.APPLE) {
            collidedEntity.setToRemove(true);
        } else if (GameEntityType.fromValue(entityType) == GameEntityType.CARROT) {
            collidedEntity.setToRemove(true);

        }

        else if (GameEntityType.fromValue(entityType) == GameEntityType.BURGER) {
            collidedEntity.setToRemove(true);
            aiPlayer.setSpeed(aiPlayer.getSpeed() / 1.5f);
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
