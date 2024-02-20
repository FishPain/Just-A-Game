package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.KeyStrokeManager;

import com.mygdx.game.movements.Movement;
import com.mygdx.game.collision.Collision;

import java.util.ArrayList;

public class Snake extends Entity {
    private ArrayList<Vector2> bodyPositions;
    private Texture headTexture, bodyTexture;
    private Movement movement;
    private final float segmentSpacing = 50; // Fixed distance between segments
    private static final float GRAVITY = -100f; // Assuming a gravity constant
    private static final int BODY_SEGMENT_COUNT = 2; // Number of body segments

    public Snake(float x, float y, float width, float height, float speed, String headTexturePath,
            String bodyTexturePath, EntityType entityType, KeyStrokeManager keyStrokeManager) {
        super(x, y, width, height, headTexturePath, speed, true, entityType);
        this.headTexture = new Texture(Gdx.files.internal(headTexturePath));
        this.bodyTexture = new Texture(Gdx.files.internal(bodyTexturePath));
        this.movement = new Movement(keyStrokeManager, x, speed, false, 0, GRAVITY);
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

        movement.applyHorizontalMovement(this, allEntities, bodyPositions, deltaTime);
        movement.applyVerticalMovement(this, allEntities, bodyPositions, deltaTime);

        updatePosition();
    }

    @Override
    public boolean isReachEnd(ArrayList<Entity> allEntities) {
        return Collision.isReachEnd(this, allEntities, movement);
    }

    @Override
    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
    }

    public ArrayList<Vector2> getBodyPositions() {
        return bodyPositions;
    }

    private void initializeBodyPositions(float x, float y) {
        for (int i = 1; i <= BODY_SEGMENT_COUNT; i++) {
            bodyPositions.add(new Vector2(x - (i * segmentSpacing), y));
        }
    }
}
