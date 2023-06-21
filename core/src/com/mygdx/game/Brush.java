package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Brush extends Solid{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x x-axis
     * @param y y-axis
     * @param s Stage
     */
    public Brush(float x, float y, Stage s) {
        super(x, y, 32, 32, s);
        loadTexture("assets/brush.png");
        setBoundaryPolygon(8);
    }
}
