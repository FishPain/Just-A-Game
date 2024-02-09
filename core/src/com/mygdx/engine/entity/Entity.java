package com.mygdx.engine.entity;

import com.badlogic.gdx.math.Shape2D;
import com.mygdx.engine.collision.Collision;

public class Entity extends Collision {

    private float x;
    private float y;
    private float rotation;

    public Entity(float x, float y, float rotate, Shape2D hitbox, boolean collidable) {
        super(hitbox, collidable);

        this.x = x;
        this.y = y;
        this.rotation = rotate;

        updatePosition(x, y, rotation);// Initial position update
    }

    public void move(float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
        updatePosition(x, y, rotation);
    }

    // If your entity rotates
    public void rotate(float angle) {
        rotation += angle;
        updatePosition(x, y, rotation);
    }

}
