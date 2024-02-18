package com.mygdx.engine.scene;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;

public class SceneManager {

    private Scene currentScene;

    private HashMap<String, Scene> scenes = new HashMap<String, Scene>();

    public SceneManager() {
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void removeScene(String name) {
        scenes.remove(name);
    }

    public Scene getScene(String name) {
        return scenes.get(name);
    }

    public ArrayList<Scene> getAllScenes() {
        return new ArrayList<Scene>(scenes.values());
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setScene(String SceneName) {
        // Get the new screen
        Scene screen = scenes.get(SceneName);
        if (screen == null) {
            throw new IllegalArgumentException("No screen with name " + SceneName + " is added to the screen manager");
        }

        // Set the new screen
        setScreen(screen);
    }

    /**
     * Sets the current screen. {@link Screen#hide()} is called on any old screen,
     * and {@link Screen#show()} is called on the new
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

}
