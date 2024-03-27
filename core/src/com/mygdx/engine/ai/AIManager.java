package com.mygdx.engine.ai;

import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;

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
}
