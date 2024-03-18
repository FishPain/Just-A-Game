package com.mygdx.game.entity;

import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig.GameEntityType;

import java.util.ArrayList;

public class Block extends Entity {
    public Block(float x, float y, float width, float height, String texturePath, GameEntityType entityType,
            boolean isVisable) {
        super(x, y, width, height, texturePath, 0, false, entityType, isVisable);
    }

    @Override
    public void move(ArrayList<Entity> allEntities, float deltaTime) {
        // Blocks typically don't move, so this may be left empty or implement logic
        // for moving blocks
    }

    @Override
    public boolean isGameEnd() {
        // Blocks typically don't move, so this may be left empty or implement logic
        // for moving blocks
        return false;
    }
}
