package com.mygdx.engine.io.button;

import java.util.ArrayList;
import com.mygdx.engine.Utils;
import com.badlogic.gdx.graphics.Texture;

public class ToggleButton extends Button {
    private ArrayList<Texture> textures;
    private int currentTextureIndex = 0;

    public ToggleButton(float x, float y, float width, float height, String buttonType, String[] texturePaths,
            String text, String fontPath, int fontSize) {
        super(x, y, width, height, buttonType, texturePaths[0], text, fontPath, fontSize);

        // Initialize the ArrayList of textures
        textures = new ArrayList<>();
        for (String path : texturePaths) {
            textures.add(new Texture(Utils.getInternalFilePath(path)));
        }
    }

    // Method to toggle between the textures
    public void toggleTexture() {
        currentTextureIndex = (currentTextureIndex + 1) % textures.size();
        setTexture(textures.get(currentTextureIndex));
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
