package com.mygdx.engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.engine.Utils;

import java.util.ArrayList;

public abstract class Entity {
    protected Texture texture;
    protected Rectangle rectangle;
    protected float x, y, width, height, speed;
    protected EntityType entityType;
    protected boolean isMovable;
    protected boolean toRemove;

    public Entity(float x, float y, float width, float height, String texturePath, float speed, boolean isMovable,
            EntityType entityType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.isMovable = isMovable;
        this.entityType = entityType;
        this.toRemove = false;
        this.texture = new Texture(Utils.getInternalFilePath(texturePath));
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public abstract void move(ArrayList<Entity> allEntities);

    public abstract boolean isGameEnd();

    public void draw(SpriteBatch batch) {
        if (batch == null || texture == null)
            return;
        batch.draw(texture, x, y, width, height);
    }

    public void updatePosition() {
        rectangle.setPosition(x, y);
    }

    public void setPosition(float x, float y) {
        rectangle.setPosition(new Vector2(x, y));
    }

    public Vector2 getPosition() {
        return rectangle.getPosition(new Vector2());
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    public void dispose() {
        texture.dispose();
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
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
