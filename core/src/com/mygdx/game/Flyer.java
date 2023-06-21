package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Flyer extends BaseActor{
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public Flyer(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("assets/enemy-flyer.png", 1, 4, 0.05f, true);
        setSize(48, 48);

        setBoundaryPolygon(6);
        setSpeed(MathUtils.random(50, 80));
        setMotionAngle(MathUtils.random(0, 360));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (MathUtils.random(1, 120) == 1) {
            setMotionAngle(MathUtils.random(0, 360));
        }
        applyPhysics(dt);
        boundToWorld();
    }
}
