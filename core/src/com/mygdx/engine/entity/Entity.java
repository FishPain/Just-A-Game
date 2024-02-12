package com.mygdx.engine.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class Entity {
    private Texture texture;
    private Rectangle rectangle;
    private float x, y, width, height, speed;
    private EntityType entityType;
    private boolean isMovable;
    private static final float GRAVITY = 200f; // Adjust as necessary, negative for downward force

    public enum EntityType {
        SNAKE_HEAD,
        SNAKE_BODY,
        PLATFORM
    }

    public Entity(float x, float y, float width, float height, String texturePath, float speed, boolean isMovable,
            EntityType entityType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.isMovable = isMovable;
        this.entityType = entityType;

        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public void draw(SpriteBatch batch) {
        if (batch == null || texture == null)
            return; // Safety check

        batch.draw(texture, x, y, width, height);
    }

    public void move(ArrayList<Entity> allEntities) {
        if (!isMovable)
            return;

        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 movementDelta = calculateMovement(deltaTime);

        // Apply gravity to all entities except platforms
        if (entityType != EntityType.PLATFORM) {
            movementDelta.y -= GRAVITY * deltaTime;
        }

        // Prepare for collision checks
        boolean horizontalCollision = false;
        boolean verticalCollision = false;
        Rectangle futureHorizontalPos = new Rectangle(rectangle.x + movementDelta.x, rectangle.y, rectangle.width,
                rectangle.height);
        Rectangle futureVerticalPos = new Rectangle(rectangle.x, rectangle.y + movementDelta.y, rectangle.width,
                rectangle.height);

        // Separately check for horizontal and vertical collisions
        for (Entity entity : allEntities) {
            if (entity == this)
                continue; // Skip self-check

            // Check for horizontal collision with a platform
            if (futureHorizontalPos.overlaps(entity.rectangle)) {
                horizontalCollision = true;
            }

            if (entity.entityType == EntityType.PLATFORM) {
                // Check for vertical collision with a platform (falling onto it)
                if (futureVerticalPos.overlaps(entity.rectangle)) {
                    verticalCollision = true;
                    // Adjust the Y position to be exactly on top of the platform
                    if (movementDelta.y < 0) { // Ensure it's moving downwards
                        movementDelta.y = entity.rectangle.y + entity.rectangle.height - this.y;
                    }
                }
            }
        }

        // Apply movement if no blocking collision detected
        if (!horizontalCollision) {
            this.x += movementDelta.x;
        }
        if (!verticalCollision) {
            this.y += movementDelta.y;
        } else {
            // Specific logic to handle landing on a platform may be added here
        }

        // Update the entity's rectangle for further collision checks
        updatePosition();
    }

    private Vector2 calculateMovement(float deltaTime) {
        Vector2 movementDelta = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movementDelta.x -= speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movementDelta.x += speed * deltaTime;
        }

        return movementDelta;
    }

    private void updatePosition() {
        rectangle.setPosition(x, y);
    }

    public void dispose() {
        if (texture != null)
            texture.dispose();
    }

    // Getters for position, to be used in collision detection or other logic
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
