package com.mygdx.engine.fonts;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.engine.Utils;

public class Font extends BitmapFont {
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    private int size;

    public Font(String fontPath, int size) {
        this.generator = new FreeTypeFontGenerator(Utils.getInternalFilePath(fontPath));
        this.parameter = new FreeTypeFontParameter();
        this.size = size;
    }

    public BitmapFont createFont() {
        parameter.size = this.size;
        return generator.generateFont(parameter);
    }

    public void setSize(int size) {
        parameter.size = size;
    }

    public int getSize() {
        return size;
    }

    public void dispose() {
        generator.dispose();
    }
}
