package com.mygdx.engine.ai;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;

import com.mygdx.game.GameConfig;
import com.mygdx.game.entity.AIPlayer;

import java.util.ArrayList;
import java.awt.Point;

public class AIManager extends EntityManager {
    // Create new AI entity

    // set movement like raindrops

    // search for apple

    // condition statement to move left, right, top, bottom
    private ArrayList<Entity> aiEntities;
    ArrayList<Point> positions = new ArrayList<Point>();

    public AIManager() {
        aiEntities = new ArrayList<Entity>();
    }

    public ArrayList<Point> getEntityPositionsByType(String entityType) {

        ArrayList<Entity> entitiesByType = getEntities(entityType);
        for (Entity entity : entitiesByType) {
            positions.add(new Point((int) entity.getX(), (int) entity.getY()));
            System.out.println("apple: " + entity.getX() + entity.getY());
        }

        return positions;
    }

    public ArrayList<Entity> getEntities(String entityType) {
        ArrayList<Entity> entitiesByType = new ArrayList<Entity>();
        for (Entity entity : aiEntities) {
            if (entity.getEntityType() == entityType) {
                entitiesByType.add(entity);
            }
        }
        return entitiesByType;
    }

    // public void setMovability(ArrayList<? extends Entity> entities, boolean
    // isMovable) {
    // for (Entity entity : entities) {
    // entity.setMovable(isMovable);
    // }
    // }

    // public void removeEntity(Entity entity) {
    // aiEntities.remove(entity);
    // }

    // public void removeEntities(ArrayList<Entity> entities) {
    // this.aiEntities.removeAll(entities);
    // }

    // public ArrayList<Entity> getEntities() {
    // return aiEntities;
    // }

    // public ArrayList<Point> getAllEntityPosition() {
    // ArrayList<Point> positions = new ArrayList<Point>();
    // for (Entity entity : aiEntities) {
    // positions.add(new Point((int) entity.getX(), (int) entity.getY()));
    // }
    // return positions;
    // }

    // // get all entities by type
    // public ArrayList<Entity> getEntities(EntityType entityType) {
    // ArrayList<Entity> entitiesByType = new ArrayList<Entity>();
    // for (Entity entity : aiEntities) {
    // if (entity.getEntityType() == entityType) {
    // entitiesByType.add(entity);
    // }
    // }
    // return entitiesByType;
    // }

    // public ArrayList<Entity> getAllCollidableEntity() {
    // ArrayList<Entity> collidableEntities = new ArrayList<Entity>();
    // for (Entity entity : aiEntities) {
    // if (entity.isCollidable()) {
    // collidableEntities.add(entity);
    // }
    // }
    // return collidableEntities;
    // }

    // public int getEntitiesCount() {
    // return aiEntities.size();
    // }

    // public int getEntitiesCount(EntityType entityType) {
    // return (int) aiEntities.stream()
    // .filter(entity -> entity.getEntityType() == entityType)
    // .count();
    // }

    // public void removeEntities() {
    // ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();
    // for (Entity entity : aiEntities) {
    // if (entity.isToRemove()) {
    // entitiesToRemove.add(entity);
    // }
    // }
    // aiEntities.removeAll(entitiesToRemove);
    // }

    // public void dispose(SpriteBatch batch) {
    // for (Entity entity : aiEntities) {
    // entity.dispose();
    // }
    // aiEntities.clear();
    // }

}
