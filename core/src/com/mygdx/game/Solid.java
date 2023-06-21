package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Solid extends BaseActor{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public Solid(float x, float y, float width, float height, Stage s) {
        super(x, y, s);
        setSize(width, height);
        setBoundaryRectangle();
    }
}
