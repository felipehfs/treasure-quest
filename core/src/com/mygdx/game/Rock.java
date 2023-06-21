package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Rock extends Solid{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public Rock(float x, float y, Stage s) {
        super(x, y, 32, 32, s);
        loadTexture("assets/rock.png");
        setBoundaryPolygon(8);
    }
}
