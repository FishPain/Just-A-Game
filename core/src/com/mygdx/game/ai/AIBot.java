package com.mygdx.game.ai;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.collision.Collision;
import com.mygdx.game.movements.Movement;

public class AIBot extends Entity{
        private ArrayList<Vector2> bodyPositions;
        private Texture headTexture, bodyTexture;
        private AIMovement movement;
        private final float segmentSpacing = GameConfig.ASSET_SIZE;
        private float angle = 0;
        private EntityManager entityManager;

        public AIBot(float x, float y, float width, float height, float speed, String headTexturePath,
                String bodyTexturePath, EntityType entityType, EntityManager entityManager) {
            super(x, y, width, height, headTexturePath, speed, true, entityType);
            this.entityManager = entityManager;
            this.headTexture = new Texture(Gdx.files.internal(headTexturePath));
            this.bodyTexture = new Texture(Gdx.files.internal(bodyTexturePath));
            this.movement = new AIMovement( x, speed, false, 0, GameConfig.GRAVITY);
            this.bodyPositions = new ArrayList<Vector2>();
            initializeBodyPositions(x, y);
        }

        @Override
        public void draw(SpriteBatch batch) {
            batch.draw(headTexture, x, y, width, height);
            for (Vector2 pos : bodyPositions) {
                batch.draw(bodyTexture, pos.x, pos.y, width, height);
            }
        }

        @Override
        public void move(ArrayList<Entity> allEntities) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            float speed = 100; // Set an appropriate speed value
            float radius = 225; // Radius of the circle
            float centerX = Gdx.graphics.getWidth() / 2; // Center of the screen (X)
            float centerY = Gdx.graphics.getHeight() / 2; // Center of the screen (Y)
    
            // Update the angle for circular movement
            angle += speed * deltaTime / radius;
            
            // Calculate new position based on the circular path
            x = (float) (centerX + radius * Math.cos(angle));
            y = (float) (centerY + radius * Math.sin(angle));
    
            // Update the body positions
            updateBodyPositions();
        }


        @Override
        public void dispose() {
            headTexture.dispose();
            bodyTexture.dispose();
        }

        private void initializeBodyPositions(float x, float y) {
            for (int i = 1; i <= GameConfig.SNAKE_BODY_LENGTH; i++) {
                bodyPositions.add(new Vector2(x - (i * segmentSpacing), y));
            }
        }

        public void autonomousUpdate() {
            float deltaTime = Gdx.graphics.getDeltaTime();
            float speed = 100; // Set an appropriate speed value
            float radius = 225; // Radius of the circle
            float centerX = Gdx.graphics.getWidth() / 2; // Center of the screen (X)
            float centerY = Gdx.graphics.getHeight() / 2; // Center of the screen (Y)
    
            // Update the angle for circular movement
            angle += speed * deltaTime / radius;
            
            // Calculate new position based on the circular path
            x = (float) (centerX + radius * Math.cos(angle));
            y = (float) (centerY + radius * Math.sin(angle));
    
            // Update the body positions
            updateBodyPositions();
    
        }
    
    
        private void updateBodyPositions() {
            // Add the current head position to the beginning of the list
            bodyPositions.add(0, new Vector2(this.x, this.y));
        
            // Update each body segment's position
            for (int i = 0; i < GameConfig.SNAKE_BODY_LENGTH; i++) {
                Vector2 newPosition = bodyPositions.get(i);
                bodyPositions.set(i, newPosition);
            }
        
            // Remove the oldest position if the list size exceeds the number of body segments
            if (bodyPositions.size() > GameConfig.SNAKE_BODY_LENGTH) {
                bodyPositions.remove(bodyPositions.size() - 1);
            }
    
    }

        @Override
        public boolean isGameEnd() {
            int targetCount = entityManager.getEntities(GameEntityType.TARGET).size();
            if (targetCount < 1) {
                return true;
            }
            return false;
        }
    
}
