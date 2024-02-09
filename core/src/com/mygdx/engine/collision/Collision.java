package com.mygdx.engine.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

/**
 * Enum defining the types of shapes supported for collision components.
 */
enum ShapeType {
    RECTANGLE,
    CIRCLE
}

/**
 * Represents a collision component that can handle both rectangular and
 * circular hitboxes,
 * enabling collision detection between entities.
 */
public class Collision {

    private Shape2D hitbox; // The hitbox for collision detection, supporting both Rectangle and Circle
                            // shapes.
    private boolean collidable; // Flag to indicate if the component is currently collidable.

    /**
     * Constructor for the Collision component.
     * 
     * @param hitbox     The hitbox shape (Rectangle or Circle) used for collision
     *                   detection.
     * @param collidable Indicates whether the hitbox should be considered
     *                   collidable.
     */
    public Collision(Shape2D hitbox, boolean collidable) {
        this.hitbox = hitbox;
        this.collidable = collidable;
    }

    /**
     * Static factory method to create a Collision component with a specified shape,
     * position, and size.
     * 
     * @param shapeType  The type of shape for the hitbox (RECTANGLE or CIRCLE).
     * @param x          The x-coordinate of the shape's position.
     * @param y          The y-coordinate of the shape's position.
     * @param width      The width (or diameter for circles) of the shape.
     * @param height     The height of the shape (ignored for circles).
     * @param collidable Whether the hitbox is collidable.
     * @return A new instance of Collision with the specified shape and properties.
     */
    public static Collision createShape(ShapeType shapeType, float x, float y, float width, float height,
            boolean collidable) {
        Shape2D hitbox;
        switch (shapeType) {
            case RECTANGLE:
                hitbox = new Rectangle(x - width / 2, y - height / 2, width, height);
                break;
            case CIRCLE:
                hitbox = new Circle(x, y, width / 2);
                break;
            default:
                throw new IllegalArgumentException("Unsupported shape type: " + shapeType);
        }
        return new Collision(hitbox, collidable);
    }

    /**
     * Updates the position of the hitbox based on the specified coordinates and
     * rotation.
     * 
     * @param x        The new x-coordinate for the hitbox.
     * @param y        The new y-coordinate for the hitbox.
     * @param rotation The rotation angle (in degrees) for the hitbox, if
     *                 applicable.
     */
    public void updatePosition(float x, float y, float rotation) {
        if (hitbox instanceof Rectangle) {
            ((Rectangle) hitbox).setPosition(x - ((Rectangle) hitbox).width / 2, y - ((Rectangle) hitbox).height / 2);
        } else if (hitbox instanceof Circle) {
            ((Circle) hitbox).setPosition(x, y);
        }
    }

    /**
     * Checks if this collision component is colliding with another specified
     * component.
     * 
     * @param other The other Collision component to check for collision.
     * @return true if there is a collision; false otherwise.
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

    public Shape2D getHitbox() {
        return hitbox;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }
}
