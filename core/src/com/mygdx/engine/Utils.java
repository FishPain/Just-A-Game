package com.mygdx.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Utils {
    // This method is used to get the internal file path defined in the assets
    // folder
    public static FileHandle getInternalFilePath(String filePath) {
        return Gdx.files.internal(filePath);
    }

}
