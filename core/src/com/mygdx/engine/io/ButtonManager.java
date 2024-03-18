package com.mygdx.engine.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.ArrayList;
import java.util.List;

public class ButtonManager extends InputAdapter {
    private List<Button> buttons = new ArrayList<>();
    private ButtonClickListener clickListener;

    public ButtonManager(ButtonClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (Button btn : buttons) {
            if (btn.isPressed(screenX, Gdx.graphics.getHeight() - screenY)) {
                clickListener.onClick(btn.getButtonType());
                return true;
            }
        }
        return false;
    }

    public void setButtonsInputProcessor() {
        Gdx.input.setInputProcessor(this);
    }

    public void drawButtons(Batch batch) {
        for (Button btn : buttons) {
            btn.draw(batch);
        }
    }

    public void dispose() {
        for (Button btn : buttons) {
            btn.dispose();
        }
    }
}
