package com.mygdx.game.collision;

import com.mygdx.engine.ai.MovementAI;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.AIPlayer;
import com.mygdx.game.entity.Player;
import com.mygdx.game.movements.Movement.Direction;

public class GameCollision extends CollisionManager {
    static MovementAI movementAI;

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
            player.setPoints(player.getPoints() + 1);
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
            player.setPoints(player.getPoints() + 1);
        }
    }

    public static void collideEffectAI(Entity collidedEntity, AIPlayer aiPlayer, String typeOfCollision) {
        String entityType = collidedEntity.getEntityType();
        GameEntityType entity = GameEntityType.fromValue(entityType);

        // Keep track of the current movement direction
        Direction currentDirection = aiPlayer.getCurrentDirection();

        if (entity == GameEntityType.BLOCK) {
            // Handle collisions based on the current movement direction
            switch (currentDirection) {
                case UP:
                    if ("horizontal".equals(typeOfCollision)) {
                        aiPlayer.moveUp(); // Continue moving up
                    } else if ("vertical".equals(typeOfCollision)) {
                        if (new Random().nextBoolean()) {
                            aiPlayer.moveRight();
                        } else {
                            aiPlayer.moveLeft();
                        }
                    }
                    break;
                case DOWN:
                    if ("horizontal".equals(typeOfCollision)) {
                        aiPlayer.moveDown(); // Continue moving down
                    } else if ("vertical".equals(typeOfCollision)) {
                        if (new Random().nextBoolean()) {
                            aiPlayer.moveRight();
                        } else {
                            aiPlayer.moveLeft();
                        }
                    }
                    break;
                case LEFT:
                    if ("vertical".equals(typeOfCollision)) {
                        aiPlayer.moveLeft(); // Continue moving left
                    } else if ("horizontal".equals(typeOfCollision)) {
                        if (new Random().nextBoolean()) {
                            aiPlayer.moveUp();
                        } else {
                            aiPlayer.moveDown();
                        }
                    }
                    break;
                case RIGHT:
                    if ("vertical".equals(typeOfCollision)) {
                        aiPlayer.moveRight(); // Continue moving right
                    } else if ("horizontal".equals(typeOfCollision)) {
                        if (new Random().nextBoolean()) {
                            aiPlayer.moveUp();
                        } else {
                            aiPlayer.moveDown();
                        }
                    }
                    break;
                default:
                    break;
            }
        } else if (entity == GameEntityType.APPLE || entity == GameEntityType.CARROT
                || entity == GameEntityType.BURGER) {
            collidedEntity.setToRemove(true);
            if (entity == GameEntityType.BURGER) {
                float speedFactor = GameConfig.BURGER_SPEED_MULTIPLIER;
                aiPlayer.setSpeed(aiPlayer.getSpeed() / speedFactor);
            } else {
                aiPlayer.setPoints(aiPlayer.getPoints() + 1);
            }
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
