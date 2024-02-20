package com.mygdx.game.entity;

import java.awt.Point;
import java.util.ArrayList;

import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;

public class PlatformManager {
    public static ArrayList<Platform> createPlatforms(ArrayList<Point> positions) {
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        for (Point position : positions) {
            Platform platform = new Platform(position.x, position.y, 50, 50, Assets.PLATFORM.getFileName(),
                    GameEntityType.PLATFORM);
            platforms.add(platform);
        }
        return platforms;
    }
}