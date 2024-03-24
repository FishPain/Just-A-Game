package com.mygdx.engine.io.button;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.engine.Utils;
import com.mygdx.engine.fonts.Font;
import com.badlogic.gdx.graphics.Texture;

public class Button extends Font {
    private float x;
    private float y;
    private float width;
    private float height;
    private String buttonType;
    private Texture texture;
    private String text;
    private BitmapFont font;

    public Button(float x, float y, float width, float height, String buttonType, String texturePath, String text,
            String fontPath, int fontSize) {
        super(fontPath, fontSize);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonType = buttonType;
        this.texture = new Texture(Utils.getInternalFilePath(texturePath));
        this.text = text;
        this.font = createFont();
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

        // Draw text centered on the button
        GlyphLayout layout = new GlyphLayout(font, text);
        float textX = x + (width - layout.width) / 2;
        float textY = y + (height + layout.height) / 2;
        font.draw(batch, layout, textX, textY);
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

    public String getButtonType() {
        return buttonType;
    }

    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}