package com.mygdx.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.engine.ai.AIManager;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.input.InputManager;
import com.mygdx.engine.io.output.OutputManager;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.controls.PlayerControlManager;

/**
 * This is the simulation lifecycle management class.
 * {@link https://libgdx.com/wiki/app/the-life-cycle}
 * 
 * @author Tony
 */
public class SimulationManager extends ApplicationAdapter {

	EntityManager EntityManager;
	CollisionManager CollisionManager;
	AIManager AIManager;
	InputManager InputManager;
	OutputManager OutputManager;
	SceneManager SceneManager;
	PlayerControlManager PlayerControlManager;

	@Override
	public void create() {
		EntityManager = new EntityManager();
		CollisionManager = new CollisionManager();

	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 1, 1);
		EntityManager.render();
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
	}

}
