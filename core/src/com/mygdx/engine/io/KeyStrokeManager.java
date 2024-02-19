package com.mygdx.engine.io;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;

public class KeyStrokeManager extends Keys {
    HashMap<KeyStrokeType, Integer> keyStrokeMap = new HashMap<KeyStrokeType, Integer>();

    // load default keystroke values
    public KeyStrokeManager(HashMap<KeyStrokeType, Integer> keyStrokeMap) {
        this.keyStrokeMap = keyStrokeMap;
    }

    // update keystroke by type
    public void updateKeyStroke(KeyStrokeType keyStrokeType, int key) {
        keyStrokeMap.put(keyStrokeType, key);
    }

    // update bulk keystrokes
    public void updateKeyStroke(HashMap<KeyStrokeType, Integer> keyStrokeMap) {
        for (KeyStrokeType keyStrokeType : keyStrokeMap.keySet()) {
            this.keyStrokeMap.put(keyStrokeType, keyStrokeMap.get(keyStrokeType));
        }
    }

    // get keystroke by type
    public int getKeyStroke(KeyStrokeType keyStrokeType) {
        return keyStrokeMap.get(keyStrokeType);
    }

    public boolean isKeyPressed(KeyStrokeType keyStrokeType) {
        return Gdx.input.isKeyPressed(getKeyStroke(keyStrokeType));
    }
}
