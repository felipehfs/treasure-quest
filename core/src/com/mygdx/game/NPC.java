package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class NPC extends BaseActor{

    private String text;
    private boolean viewing;
    private String ID;
    /**
     * Set initial position of actor and add to stage.
     *
     * @param x
     * @param y
     * @param s
     */
    public NPC(float x, float y, Stage s) {
        super(x, y, s);
        text = " ";
        viewing = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isViewing() {
        return viewing;
    }

    public void setViewing(boolean viewing) {
        this.viewing = viewing;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
        if (ID.equals("Gatekeeper"))
            loadTexture("assets/npc-1.png");
        else if (ID.equals("Shopkeeper"))
            loadTexture("assets/npc-2.png");
        else
            loadTexture("assets/npc-3.png");
    }
}
