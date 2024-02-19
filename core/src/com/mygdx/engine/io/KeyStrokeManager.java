package com.mygdx.engine.io;

import com.badlogic.gdx.Gdx;
import com.mygdx.engine.Utils;

import java.util.HashMap;
import org.json.JSONObject;

// Assuming KeyStrokeType is an enum you've defined
public class KeyStrokeManager {
    private HashMap<String, Integer> keyStrokeMap;

    // Constructor to load default keystroke values
    public KeyStrokeManager(String filePath) {
        this.keyStrokeMap = new HashMap<>();
        // Load the default key strokes from the file
        loadKeyStrokes(filePath);
    }

    // Method to get the key code for a specific KeyStrokeType
    public Integer getKeyCode(String keyStrokeType) {
        return keyStrokeMap.get(keyStrokeType);
    }

    public HashMap<String, Integer> getKeyStrokeMap() {
        return keyStrokeMap;
    }

    public void setKeyCode(String keyStrokeType, int keyCode) {
        keyStrokeMap.put(keyStrokeType, keyCode);
    }

    public boolean isKeyPressed(String keyStrokeType) {
        return Gdx.input.isKeyPressed(keyStrokeMap.get(keyStrokeType));
    }

    public void saveKeyStrokes(String filePath) {
        JSONObject json = new JSONObject();
        json.put("KeyStrokes", keyCodeToKeyStroke());
        try {
            Gdx.files.local(filePath).writeString(json.toString(), false);
            System.out.println("KeyStrokes saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading or writing the settings file.");
        }
    }

    private void loadKeyStrokes(String filePath) {
        String jsonContent;
        // if local file is available, load it, else load the default file
        if (Gdx.files.local(filePath).exists()) {
            jsonContent = Gdx.files.local(filePath).readString();
        } else {
            jsonContent = Gdx.files.internal(filePath).readString();
        }

        try {
            Object json = new JSONObject(jsonContent).get("KeyStrokes");
            for (String key : ((JSONObject) json).keySet()) {
                keyStrokeMap.put(key, Utils.keyNameToKeyCode((String) ((JSONObject) json).get(key)));
            }
            System.out.println("KeyStrokes loaded successfully." + keyStrokeMap.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading or writing the settings file.");
        }
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
}
