package com.mygdx.engine.io;

import com.badlogic.gdx.Gdx;
import com.mygdx.engine.Utils;
import com.mygdx.engine.Utils.Settings;

import java.util.HashMap;
import org.json.JSONObject;

public class KeyStrokeManager extends Settings {
    private HashMap<String, Integer> keyStrokeMap;

    // Constructor to load default keystroke values
    public KeyStrokeManager(String filePath) {
        this.keyStrokeMap = new HashMap<>();
        // Load the default key strokes from the file
        loadKeyStrokes(filePath);
    }

    // Method to get the key code for a specific KeyStrokeType
    public Integer getKeyCode(String keyStrokeName) {
        return keyStrokeMap.get(keyStrokeName);
    }

    public HashMap<String, Integer> getKeyStrokeMap() {
        return keyStrokeMap;
    }

    public void setKeyCode(String keyStrokeName) {
        int keyCode = getKeyCode(keyStrokeName);
        keyStrokeMap.put(keyStrokeName, keyCode);
    }

    public boolean isKeyPressed(String keyStrokeName) {
        return Gdx.input.isKeyPressed(keyStrokeMap.get(keyStrokeName));
    }

    public String getKeyPressed() {
        for (String key : keyStrokeMap.keySet()) {
            if (Gdx.input.isKeyPressed(keyStrokeMap.get(key))) {
                return key;
            }
        }
        return null;
    }

    public void saveKeyStrokes(String filePath) {
        saveSettings(filePath, "KeyStrokes", keyCodeToKeyStroke());
    }

    private void loadKeyStrokes(String filePath) {
        Object json = loadSettings(filePath, "KeyStrokes");
        for (String key : ((JSONObject) json).keySet()) {
            keyStrokeMap.put(key, Utils.keyNameToKeyCode((String) ((JSONObject) json).get(key)));
        }
        System.out.println("KeyStrokes loaded successfully." + keyStrokeMap.toString());
    }

    private HashMap<String, String> keyCodeToKeyStroke() {
        HashMap<String, String> temp = new HashMap<>();
        try {
            for (String key : keyStrokeMap.keySet()) {
                temp.put(key, Utils.keyCodeToKeyName(keyStrokeMap.get(key)));
            }
            System.out.println("KeyStrokes loaded successfully." + temp.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading or writing the settings file.");
        }
        return temp;
    }

    public void dispose() {
        keyStrokeMap.clear();
    }
}
