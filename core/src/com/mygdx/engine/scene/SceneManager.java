package com.mygdx.engine.scene;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.scenes.MainMenu;

public class SceneManager extends InputAdapter {

    private Scene currentScene;
    private HashMap<SceneType, Scene> scenes = new HashMap<SceneType, Scene>();

    public SceneManager() {
    }

    public void addScene(SceneType SceneType, Scene scene) {
        scenes.put(SceneType, scene);
    }

    public void removeScene(SceneType SceneType) {
        scenes.remove(SceneType);
    }

    public Scene getScene(SceneType SceneType) {
        return scenes.get(SceneType);
    }

    public ArrayList<Scene> getAllScenes() {
        return new ArrayList<Scene>(scenes.values());
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setScene(SceneType SceneType) {
        // Get the new screen
        Scene screen = scenes.get(SceneType);
        if (screen == null) {
            throw new IllegalArgumentException("No screen with name " + SceneType + " is added to the screen manager");
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
