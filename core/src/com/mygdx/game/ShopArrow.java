package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class ShopArrow extends BaseActor{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public ShopArrow(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/arrow-icon.png");
    }
}
