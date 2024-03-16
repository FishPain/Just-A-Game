package com.mygdx.game.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;

import com.badlogic.gdx.math.Rectangle;

public class PlatformManager {
    private static final int MAX_ATTEMPTS = 100;

    public ArrayList<Platform> createPlatforms(ArrayList<Point> platformPositions) {
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        for (Point position : platformPositions) {
            Platform platform = new Platform(position.x, position.y, GameConfig.PLATFORM_SIZE, GameConfig.PLATFORM_SIZE,
                    Assets.PLATFORM.getFileName(),
                    GameEntityType.PLATFORM, true);
            platforms.add(platform);
        }
        return platforms;
    }

    public Platform createExitPortal(Point position) {
        Platform portal = new Platform(position.x, position.y, GameConfig.PLATFORM_SIZE, GameConfig.PLATFORM_SIZE,
                Assets.EXIT_PORTAL.getFileName(),
                GameEntityType.EXIT_PORTAL, false);
        portal.setCollidable(false);
        return portal;
    }

    public ArrayList<Platform> createRandomPlatforms(int numOfPlatforms, ArrayList<Point> allEntityPosition,
            String assetFilePath, GameEntityType entityType) {
        ArrayList<Platform> obstacles = new ArrayList<>();
        Random rand = new Random();
        int attempts;

        for (int i = 0; i < numOfPlatforms; i++) {
            boolean positionFound;
            Point potentialPosition = null;

            for (attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                positionFound = true;
                int x = rand.nextInt(GameConfig.SCREEN_WIDTH - GameConfig.PLATFORM_SIZE);
                int y = rand.nextInt(GameConfig.SCREEN_HEIGHT - GameConfig.PLATFORM_SIZE);
                potentialPosition = new Point(x, y);

                Rectangle potentialRect = new Rectangle(x, y, GameConfig.PLATFORM_SIZE, GameConfig.PLATFORM_SIZE);

                for (Point entityPos : allEntityPosition) {
                    Rectangle entityRect = new Rectangle(entityPos.x, entityPos.y, GameConfig.PLATFORM_SIZE,
                            GameConfig.PLATFORM_SIZE);
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
                Platform platform = new Platform(potentialPosition.x, potentialPosition.y, GameConfig.PLATFORM_SIZE,
                        GameConfig.PLATFORM_SIZE,
                        assetFilePath, entityType, true);
                obstacles.add(platform);
            }
        }

        return obstacles;
    }
}