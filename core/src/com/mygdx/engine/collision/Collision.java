package com.mygdx.engine.collision;

import com.mygdx.engine.entity.Entity;

public class Collision {
    private Entity entityA;
    private Entity entityB;

    public Collision(Entity entityA, Entity entityB) {
        this.entityA = entityA;
        this.entityB = entityB;
    }

    public Entity getEntityA() {
        return entityA;
    }

    public Entity getEntityB() {
        return entityB;
    }
}
