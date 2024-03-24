package com.mygdx.game.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;

import com.badlogic.gdx.math.Rectangle;

public class BlockManager {
    private static final int MAX_ATTEMPTS = 100;

    public ArrayList<Block> createBlocks(ArrayList<Point> blockPositions) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (Point position : blockPositions) {
            Block block = new Block(position.x, position.y, GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE,
                    Assets.BLOCK.getFileName(),
                    GameEntityType.BLOCK.getValue(), true, false);
            blocks.add(block);
        }
        return blocks;
    }

    public Block createExitPortal(Point position) {
        Block portal = new Block(position.x, position.y, GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE,
                Assets.EXIT_PORTAL.getFileName(),
                GameEntityType.EXIT_PORTAL.getValue(), false, true);
        portal.setCollidable(false);
        return portal;
    }

    public ArrayList<Block> createRandomBlocks(int numOfBlocks, ArrayList<Point> allEntityPosition,
            String assetFilePath, String entityType) {
        ArrayList<Block> obstacles = new ArrayList<>();
        Random rand = new Random();
        int attempts;

        for (int i = 0; i < numOfBlocks; i++) {
            boolean positionFound;
            Point potentialPosition = null;

            for (attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                positionFound = true;
                int x = rand.nextInt(GameConfig.SCREEN_WIDTH - GameConfig.BLOCK_SIZE);
                int y = rand.nextInt(GameConfig.SCREEN_HEIGHT - GameConfig.BLOCK_SIZE);
                potentialPosition = new Point(x, y);

                Rectangle potentialRect = new Rectangle(x, y, GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE);

                for (Point entityPos : allEntityPosition) {
                    Rectangle entityRect = new Rectangle(entityPos.x, entityPos.y, GameConfig.BLOCK_SIZE,
                            GameConfig.BLOCK_SIZE);
                    if (potentialRect.overlaps(entityRect)) {
                        positionFound = false;
                        break;
                    }
                }

                if (positionFound) {
                    allEntityPosition.add(potentialPosition);
                    break;
                }
            }

            if (attempts < MAX_ATTEMPTS && potentialPosition != null) {
                boolean isInteractable = entityType.equals(GameEntityType.BLOCK.getValue()) ? false : true;

                Block block = new Block(potentialPosition.x, potentialPosition.y, GameConfig.BLOCK_SIZE,
                        GameConfig.BLOCK_SIZE,
                        assetFilePath, entityType, true, isInteractable);
                obstacles.add(block);
            }
        }

        return obstacles;
    }
}