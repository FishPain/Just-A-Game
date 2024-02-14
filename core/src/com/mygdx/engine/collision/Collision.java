package com.mygdx.engine.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

/**
 * The {@code Collision} class represents a collision component capable of
 * handling
 * both rectangular and circular hitboxes. It enables collision detection
 * between
 * entities by encapsulating a shape (either a {@link Rectangle} or a
 * {@link Circle})
 * as its hitbox. This class supports basic collision detection functionalities
 * such as updating hitbox positions and checking for collisions between
 * entities.
 */
public class Collision {

    /**
     * The hitbox for collision detection, supporting both {@link Rectangle} and
     * {@link Circle} shapes.
     */
    private Shape2D hitbox;

    /** Flag to indicate whether the component is currently collidable. */
    private boolean collidable;

    /**
     * Defines the types of shapes supported for collision components.
     */
    public enum ShapeType {
        RECTANGLE, // Represents a rectangular shape for the hitbox.
        CIRCLE // Represents a circular shape for the hitbox.
    }

    /**
     * Constructs a new {@code Collision} component with the specified parameters.
     *
     * @param shapeType  The type of shape for the hitbox, either {@code RECTANGLE}
     *                   or {@code CIRCLE}.
     * @param x          The x-coordinate of the shape's position.
     * @param y          The y-coordinate of the shape's position.
     * @param width      The width of the shape (or diameter for circles).
     * @param height     The height of the shape (ignored for circles).
     * @param collidable A boolean indicating whether the hitbox should be
     *                   considered collidable.
     * @throws IllegalArgumentException if an unsupported shape type is provided.
     */
    public Collision(ShapeType shapeType, float x, float y, float width, float height, boolean collidable) {
        this.collidable = collidable;

        switch (shapeType) {
            case RECTANGLE:
                this.hitbox = new Rectangle(x - width / 2, y - height / 2, width, height);
                break;
            case CIRCLE:
                this.hitbox = new Circle(x, y, width / 2);
                break;
            default:
                throw new IllegalArgumentException("Unsupported shape type: " + shapeType);
        }
    }

    /**
     * Updates the position and rotation of the hitbox based on specified
     * coordinates and rotation angle.
     *
     * @param x The new x-coordinate for the hitbox.
     * @param y The new y-coordinate for the hitbox.
     */
    public void updateHitboxPosition(float x, float y) {
        if (hitbox instanceof Rectangle) {
            ((Rectangle) hitbox).setPosition(x - ((Rectangle) hitbox).width / 2, y - ((Rectangle) hitbox).height / 2);
        } else if (hitbox instanceof Circle) {
            ((Circle) hitbox).setPosition(x, y);
        }
    }

    /**
     * Checks if this collision component is colliding with another specified
     * collision component.
     *
     * @param other The other {@code Collision} component to check for collision
     *              against.
     * @return {@code true} if there is a collision; {@code false} otherwise.
     */
    public boolean isColliding(Collision other) {
        if (!collidable || !other.isCollidable()) {
            return false;
        }

        if (hitbox instanceof Rectangle && other.getHitbox() instanceof Rectangle) {
            return ((Rectangle) hitbox).overlaps((Rectangle) other.getHitbox());
        } else if (hitbox instanceof Circle && other.getHitbox() instanceof Circle) {
            return Intersector.overlaps((Circle) hitbox, (Circle) other.getHitbox());
        } else if (hitbox instanceof Rectangle && other.getHitbox() instanceof Circle) {
            return Intersector.overlaps((Circle) other.getHitbox(), (Rectangle) hitbox);
        } else if (hitbox instanceof Circle && other.getHitbox() instanceof Rectangle) {
            return Intersector.overlaps((Circle) hitbox, (Rectangle) other.getHitbox());
        }

        return false;
    }

    // Getters and Setters

    /**
     * Returns the hitbox associated with this collision component.
     *
     * @return The {@code Shape2D} hitbox for this component.
     */
    public Shape2D getHitbox() {
        return hitbox;
    }

    /**
     * Returns whether this collision component is currently collidable.
     *
     * @return {@code true} if this component is collidable; {@code false}
     *         otherwise.
     */
    public boolean isCollidable() {
        return collidable;
    }

    /**
     * Sets whether this collision component should be considered collidable.
     *
     * @param collidable {@code true} to make the component collidable;
     *                   {@code false} otherwise.
     */
    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }
}
