package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Treasure extends  BaseActor{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public Treasure(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/treasure-chest.png");
    }
}
