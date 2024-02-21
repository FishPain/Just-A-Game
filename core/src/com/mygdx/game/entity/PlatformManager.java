package com.mygdx.game.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;

import com.badlogic.gdx.math.Rectangle;

public class PlatformManager {
    private static final int SCREEN_WIDTH = 800; // Example screen width
    private static final int SCREEN_HEIGHT = 600; // Example screen height
    private static final int ASSET_SIZE = 50; // Example asset size
    private static final int MAX_ATTEMPTS = 100; // Prevent infinite loops

    public ArrayList<Platform> createPlatforms(ArrayList<Point> platformPositions) {
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        for (Point position : platformPositions) {
            Platform platform = new Platform(position.x, position.y, 50, 50, Assets.PLATFORM.getFileName(),
                    GameEntityType.PLATFORM);
            platforms.add(platform);
        }
        return platforms;
    }

    public ArrayList<Platform> createRandomPlatforms(int numOfPlatforms, ArrayList<Point> allEntityPosition, String assetFilePath, GameEntityType entityType) {
        ArrayList<Platform> obstacles = new ArrayList<>();
        Random rand = new Random();
        int attempts;

        for (int i = 0; i < numOfPlatforms; i++) {
            boolean positionFound;
            Point potentialPosition = null;

            for (attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                positionFound = true;
                int x = rand.nextInt(SCREEN_WIDTH - ASSET_SIZE);
                int y = rand.nextInt(SCREEN_HEIGHT - ASSET_SIZE);
                potentialPosition = new Point(x, y);

                Rectangle potentialRect = new Rectangle(x, y, ASSET_SIZE, ASSET_SIZE);

                for (Point entityPos : allEntityPosition) {
                    Rectangle entityRect = new Rectangle(entityPos.x, entityPos.y, ASSET_SIZE, ASSET_SIZE);
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
                Platform platform = new Platform(potentialPosition.x, potentialPosition.y, ASSET_SIZE, ASSET_SIZE,
                        assetFilePath, entityType);
                obstacles.add(platform);
            }
        }

        return obstacles;
    }
}