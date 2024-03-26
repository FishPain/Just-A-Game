package com.mygdx.engine.scene;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.InputAdapter;

public class SceneManager extends InputAdapter {

    private Scene currentScene;
    private Scene previousScene; // Store the previous scene
    private HashMap<String, Scene> scenes = new HashMap<String, Scene>();

    public SceneManager() {
    }

    public void addScene(Scene scene) {
        scenes.put(scene.getSceneType(), scene);
    }

    public void removeScene(String sceneType) {
        scenes.remove(sceneType);
    }

    public Scene getScene(String sceneType) {
        return scenes.get(sceneType);
    }

    public ArrayList<Scene> getAllScenes() {
        return new ArrayList<Scene>(scenes.values());
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setScene(String sceneType) {
        // Get the new screen
        Scene newScene = scenes.get(sceneType);
        if (newScene == null) {
            throw new IllegalArgumentException("No screen with name " + sceneType + " is added to the screen manager");
        }

        // Set the previous scene
        previousScene = currentScene;

        // Set the new scene
        setScreen(newScene);
    }

    /**
     * Sets the current screen. {@link Scene#hide()} is called on any old screen,
     * and {@link Scene#show()} is called on the new
     * screen, if any.
     * 
     * @param screen may be {@code null}
     */
    public void setScreen(Scene screen) {
        if (this.currentScene != null)
            this.currentScene.hide();
        this.currentScene = screen;
        if (this.currentScene != null) {
            this.currentScene.show();
        }
    }

    public void dispose() {
        for (Scene screen : scenes.values()) {
            screen.dispose();
        }
    }
}
