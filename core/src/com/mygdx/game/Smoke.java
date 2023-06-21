package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Smoke extends BaseActor{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public Smoke(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/smoke.png");
        addAction(Actions.fadeOut(0.5f));
        addAction(Actions.after(Actions.removeActor()));
    }

}
