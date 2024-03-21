package com.mygdx.engine;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

import org.json.JSONObject;

public class Utils {
    public static class Settings {
        public static Object loadSettings(String filePath, String key) {
            String jsonContent;

            // if local file is available, load it, else load the default file
            if (Gdx.files.local(filePath).exists()) {
                jsonContent = Gdx.files.local(filePath).readString();
            } else {
                jsonContent = Gdx.files.internal(filePath).readString();
            }

            try {
                System.out.println("Settings loaded successfully.");
                return new JSONObject(jsonContent).get(key);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error reading or writing the settings file.");
                return null;
            }
        }

        public static void saveSettings(String filePath, String key, HashMap<String, String> settings) {
            JSONObject json = new JSONObject();
            json.put(key, settings);
            try {
                Gdx.files.local(filePath).writeString(json.toString(), false);
                System.out.println("KeyStrokes saved successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error reading or writing the settings file.");
            }
        }
    }

    // This method is used to get the internal file path defined in the assets
    // folder
    public static FileHandle getInternalFilePath(String filePath) {
        return Gdx.files.internal(filePath);
    }

    // convert string to int using switch statement
    public static final int keyNameToKeyCode(String key) {
        int result = -1;
        switch (key) {
            case "A":
                result = 29;
                break;
            case "B":
                result = 30;
                break;
            case "C":
                result = 31;
                break;
            case "D":
                result = 32;
                break;
            case "E":
                result = 33;
                break;
            case "F":
                result = 34;
                break;
            case "G":
                result = 35;
                break;
            case "H":
                result = 36;
                break;
            case "I":
                result = 37;
                break;
            case "J":
                result = 38;
                break;
            case "K":
                result = 39;
                break;
            case "L":
                result = 40;
                break;
            case "M":
                result = 41;
                break;
            case "N":
                result = 42;
                break;
            case "O":
                result = 43;
                break;
            case "P":
                result = 44;
                break;
            case "Q":
                result = 45;
                break;
            case "R":
                result = 46;
                break;
            case "S":
                result = 47;
                break;
            case "T":
                result = 48;
                break;
            case "U":
                result = 49;
                break;
            case "V":
                result = 50;
                break;
            case "W":
                result = 51;
                break;
            case "X":
                result = 52;
                break;
            case "Y":
                result = 53;
                break;
            case "Z":
                result = 54;
                break;
            case "SPACE":
                result = 62;
                break;
            case "SHIFT_LEFT":
                result = 59;
                break;
            case "SHIFT_RIGHT":
                result = 60;
                break;
            case "ESCAPE":
                result = 111;
                break;
            default:
                System.out.println("Key not handled: " + key);
                break;
        }
        return result;
    }

    public static final String keyCodeToKeyName(int keyCode) {
        switch (keyCode) {
            case 29:
                return "A";
            case 30:
                return "B";
            case 31:
                return "C";
            case 32:
                return "D";
            case 33:
                return "E";
            case 34:
                return "F";
            case 35:
                return "G";
            case 36:
                return "H";
            case 37:
                return "I";
            case 38:
                return "J";
            case 39:
                return "K";
            case 40:
                return "L";
            case 41:
                return "M";
            case 42:
                return "N";
            case 43:
                return "O";
            case 44:
                return "P";
            case 45:
                return "Q";
            case 46:
                return "R";
            case 47:
                return "S";
            case 48:
                return "T";
            case 49:
                return "U";
            case 50:
                return "V";
            case 51:
                return "W";
            case 52:
                return "X";
            case 53:
                return "Y";
            case 54:
                return "Z";
            case 62:
                return "SPACE";
            case 59:
                return "SHIFT_LEFT";
            case 60:
                return "SHIFT_RIGHT";
            case 61:
                return "TAB";
            case 111:
                return "ESCAPE";
            // Include F1-F12 and other keys if their codes are defined
            default:
                return "UNKNOWN";
        }
    }

}
