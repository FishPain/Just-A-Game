package com.mygdx.engine.entity;

import java.util.ArrayList;
import com.mygdx.engine.entity.Entity.EntityType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {
    private ArrayList<Entity> entities;
    private SpriteBatch batch;

    public EntityManager() {
        batch = new SpriteBatch();
        batch.begin();

        entities = new ArrayList<Entity>();
        entities.add(
                new Entity(500, 300, 50, 50, "snakeHead.jpg", 200, true,
                        EntityType.SNAKE_HEAD));
        entities.add(
                new Entity(550, 300, 50, 50, "snakeBody.jpg", 200, true,
                        EntityType.SNAKE_BODY));

        for (int i = 0; i < 10; i++) {
            entities.add(
                    new Entity(400 + i * 50, 100, 50, 50, "stoneTex.jpg", 200, false,
                            EntityType.PLATFORM));
        }

        entities.add(
                new Entity(400, 150, 50, 50, "stoneTex.jpg", 200, false,
                        EntityType.PLATFORM));

    }

    public void addEntity(ArrayList<Entity> EntityArray, Entity entity) {
        EntityArray.add(entity);
    }

    public void removeEntity(ArrayList<Entity> EntityArray, Entity entity) {
        EntityArray.remove(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void render() {
        // Assuming SpriteBatch is passed as a parameter for efficiency
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
