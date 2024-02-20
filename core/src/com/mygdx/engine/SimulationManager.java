package com.mygdx.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.engine.scene.SceneType;

public class SimulationManager extends ApplicationAdapter {
	private SoundEffects gameTheme;
	private SceneType currentScene;
	
	@Override
    public void create() {
        gameTheme = new SoundEffects("sounds/gameTheme.mp3");
        gameTheme.play(1.0f); // Play the sound at full volume
    }

	@Override
	public void render() {
		currentScene.getSound().play(1.0f);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
    public void dispose() {
        gameTheme.dispose();
    }

}