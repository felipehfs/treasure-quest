package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Arrow extends BaseActor{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public Arrow(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/arrow.png");
        setSpeed(400);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
    }
}
