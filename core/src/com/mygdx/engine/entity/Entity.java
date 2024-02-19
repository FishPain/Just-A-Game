package com.mygdx.engine.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.engine.collision.iCollide;
import com.mygdx.engine.Utils;

import java.util.ArrayList;

public abstract class Entity implements iCollide {
    protected Texture texture;
    protected Rectangle rectangle;
    protected float x, y, width, height, speed;
    protected EntityType entityType;
    protected boolean isMovable;
    protected static final float GRAVITY = 200f;

    public Entity(float x, float y, float width, float height, String texturePath, float speed, boolean isMovable,
            EntityType entityType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.isMovable = isMovable;
        this.entityType = entityType;

        this.texture = new Texture(Utils.getInternalFilePath(texturePath));
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public abstract void move(ArrayList<Entity> allEntities);

    public void draw(SpriteBatch batch) {
        if (batch == null || texture == null)
            return;
        batch.draw(texture, x, y, width, height);
    }

    protected void updatePosition() {
        rectangle.setPosition(x, y);
    }

    public void dispose() {
        if (texture != null)
            texture.dispose();
    }

    public float getX() {
        return rectangle.x;
    }

    public void setX(float x) {
        this.rectangle.x = x;
    }

    public float getY() {
        return rectangle.y;
    }

    public void setY(float y) {
        this.rectangle.y = y;
    }

    public float getWidth() {
        return rectangle.width;
    }

    public void setWidth(float width) {
        rectangle.width = width;
    }

    public float getHeight() {
        return rectangle.height;
    }

    public void setHeight(float height) {
        rectangle.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTexture(String texturePath) {
        this.texture = new Texture(Utils.getInternalFilePath(texturePath));
    }
}
