package com.mygdx.engine.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Button {
    private float x;
    private float y;
    private float width;
    private float height;
    private ButtonType buttonType;
    private Texture texture;

    public Button(float x, float y, float width, float height, ButtonType buttonType, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonType = buttonType;
        this.texture = new Texture(Gdx.files.internal(texturePath));
    }

    // Method to check if the button is pressed
    public boolean isPressed(int screenX, int screenY) {
        return (screenX >= x && screenX <= x + width && screenY >= y && screenY <= y + height);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void draw(Batch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public void dispose() {
        texture.dispose();
    }
}
