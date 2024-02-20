package com.mygdx.engine.ai;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class MovingEntity {
    private Sprite sprite;
    private List<Node> path; // Updated to use List<Node>
    private float speed;
    private int currentPathIndex;

    public MovingEntity(Sprite sprite, List<Node> path, float speed) {
        this.sprite = sprite;
        this.path = path;
        this.speed = speed;
        this.currentPathIndex = 0;
    }

    public void update(float deltaTime) {
        if (currentPathIndex < path.size()) {
            Node targetNode = path.get(currentPathIndex);
            Vector2 target = new Vector2(targetNode.x, targetNode.y); // Convert Node to Vector2
            Vector2 position = new Vector2(sprite.getX(), sprite.getY());
            Vector2 direction = target.cpy().sub(position).nor();
            Vector2 movement = direction.scl(speed * deltaTime);
            
            sprite.setPosition(sprite.getX() + movement.x, sprite.getY() + movement.y);

            if (position.dst(target) < 1) { // Threshold to reach the target
                currentPathIndex++;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
