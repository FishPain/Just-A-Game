package com.mygdx.engine.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffects {
    private Sound sound;

    public SoundEffects(String filePath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
    }

    public void play(float volume) {
        sound.play(volume);
    }

    public void dispose() {
        sound.dispose();
    }
}
