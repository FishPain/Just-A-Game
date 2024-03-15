package com.mygdx.engine.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Texture texture;

    public Button(int x, int y, int width, int height, String text, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public void dispose() {
        texture.dispose();
    }
}
