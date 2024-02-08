package com.mygdx.engine;

import com.badlogic.gdx.ApplicationAdapter;
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

	EntityManager EntityManager = new EntityManager();
	CollisionManager CollisionManager = new CollisionManager();
	AIManager AIManager = new AIManager();
	InputManager InputManager = new InputManager();
	OutputManager OutputManager = new OutputManager();
	SceneManager SceneManager = new SceneManager();
	PlayerControlManager PlayerControlManager = new PlayerControlManager();

	@Override
	public void create() {
	}

	@Override
	public void render() {
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
