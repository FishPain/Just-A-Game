package com.mygdx.game.entity;

import com.mygdx.engine.entity.Entity;
import java.util.ArrayList;

public class Platform extends Entity {
    public Platform(float x, float y, float width, float height, String texturePath, EntityType entityType) {
        super(x, y, width, height, texturePath, 0, false, entityType);
    }

    @Override
    public void move(ArrayList<Entity> allEntities) {
        // Platforms typically don't move, so this may be left empty or implement logic
        // for moving platforms
    }
}