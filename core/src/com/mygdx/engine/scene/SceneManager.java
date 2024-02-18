package com.mygdx.engine.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager extends Scene {

    private Game game;
    private SpriteBatch batch;

    public SceneManager(Game game) {
        this.game = game;
        this.batch = new SpriteBatch();
    }

    public void setScene(Scene screen) {
        // Dispose the current screen if needed
        if (game.getScreen() != null) {
            game.getScreen().dispose();
        }

        // Set the new screen
        game.setScreen(screen);
        screen.setBatch(batch);
        screen.show();
    }

    public void render(float delta) {
        // Render the current screen
        if (game.getScreen() instanceof Scene) {
            ((Scene) game.getScreen()).render(delta);
        }
    }

    public void dispose() {
        // Dispose resources when the screen manager is no longer needed
        batch.dispose();
    }
}
