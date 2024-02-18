package com.mygdx.engine.entity;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {
    private ArrayList<Entity> entities;
    private SpriteBatch batch;

    public EntityManager() {
        batch = new SpriteBatch();
        entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void render() {
        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entities);
        }
    }

    public void dispose() {
        for (Entity entity : entities) {
            entity.dispose();
        }
        batch.end();
        batch.dispose();
    }

}
