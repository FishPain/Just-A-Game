package com.mygdx.engine.entity;

import com.mygdx.game.GameConfig;

import java.util.ArrayList;
import java.awt.Point;

public class EntityManager {
    private ArrayList<Entity> entities;
    private ArrayList<Point> allApplePositionArrayList;

    public EntityManager() {
        entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addEntities(ArrayList<? extends Entity> entities) {
        this.entities.addAll(entities);
    }

    public void setMovability(ArrayList<? extends Entity> entities, boolean isMovable) {
        for (Entity entity : entities) {
            entity.setMovable(isMovable);
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void removeEntities(ArrayList<Entity> entities) {
        this.entities.removeAll(entities);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Entity> getEntitiesByInteractability(boolean isInteractable) {
        ArrayList<Entity> EntitiesByInteractability = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.isInteractable() == isInteractable) {
                EntitiesByInteractability.add(entity);
            }
        }
        return EntitiesByInteractability;
    }

    public ArrayList<Point> getAllEntityPosition() {
        ArrayList<Point> positions = new ArrayList<Point>();
        if (entities == null) {

        } else {
            for (Entity entity : entities) {
                positions.add(new Point((int) entity.x, (int) entity.y));
            }
        }
        return positions;

    }

    // get all entities by type
    public ArrayList<Point> getAllEntityPosition(String entityType) {
        ArrayList<Point> positions = new ArrayList<Point>();
        for (Entity entity : getEntities(entityType)) {
            positions.add(new Point((int) entity.x, (int) entity.y));
        }
        return positions;
    }

    // get all entities by type
    public ArrayList<Entity> getEntities(String entityType) {
        ArrayList<Entity> entitiesByType = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.getEntityType() == entityType) {
                entitiesByType.add(entity);
            }
        }
        return entitiesByType;
    }

    public ArrayList<Point> getAllAISnakePosition() {
        return this.getAllEntityPosition(GameConfig.GameEntityType.AI_PLAYER.toString());
    }

    public ArrayList<Point> getAllApplePosition() {
        // get all the entity positions of type APPLE by passing in the entityType
        if (this.getAllEntityPosition(GameConfig.GameEntityType.APPLE.toString()).isEmpty()) {
            return new ArrayList<Point>();
        } else {
            allApplePositionArrayList = this.getAllEntityPosition(GameConfig.GameEntityType.APPLE.toString());

            System.out.println(
                    "Apple1: " + (allApplePositionArrayList.get(0).x) + " , "
                            + (allApplePositionArrayList.get(0).y));
            System.out.println("All Apple: " + (allApplePositionArrayList));

            // return the list of all apple positions
            return allApplePositionArrayList;
        }

    }

    public ArrayList<Entity> getAllCollidableEntity() {
        ArrayList<Entity> collidableEntities = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.isCollidable()) {
                collidableEntities.add(entity);
            }
        }
        return collidableEntities;
    }

    public int getEntitiesCount() {
        return entities.size();
    }

    public int getEntitiesCount(String entityType) {
        return (int) entities.stream()
                .filter(entity -> entity.getEntityType() == entityType)
                .count();
    }

    public void removeEntities() {
        ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.isToRemove()) {
                entitiesToRemove.add(entity);
            }
        }
        entities.removeAll(entitiesToRemove);
    }

    public void dispose() {
        for (Entity entity : entities) {
            entity.dispose();
        }
        entities.clear();
    }
}
