package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LevelScreen extends BaseScreen {
    Hero hero;
    Sword sword;

    int health;
    int coins;
    int arrows;
    boolean gameOver;
    Label healthLabel;
    Label coinLabel;
    Label arrowLabel;
    Label messageLabel;
    DialogBox dialogBox;

    Treasure treasure;
    ShopHeart shopHeart;
    ShopArrow shopArrow;

    public void initialize() {
        TilemapActor tma = new TilemapActor("assets/map.tmx", mainStage);
        for (MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            new Solid((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"), mainStage);
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero((float) startProps.get("x"), (float) startProps.get("y"), mainStage);

        sword = new Sword(0, 0, mainStage);
        sword.setVisible(false);


        for (MapObject obj : tma.getTileList("Brush")) {
            MapProperties props = obj.getProperties();
            new Brush((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getTileList("Rock")) {
            MapProperties props = obj.getProperties();
            new Rock((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        health = 3;
        coins = 5;
        arrows = 3;
        gameOver = false;

        healthLabel = new Label(" x " + healthLabel, BaseGame.labelStyle);
        healthLabel.setColor(Color.PINK);
        coinLabel = new Label(" x " + coins, BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        arrowLabel = new Label(" x " + arrows, BaseGame.labelStyle);
        arrowLabel.setColor(Color.TAN);
        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setVisible(false);

        dialogBox = new DialogBox(0, 0, uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        BaseActor healthIcon = new BaseActor(0, 0, uiStage);
        healthIcon.loadTexture("assets/heart-icon.png");

        BaseActor coinIcon = new BaseActor(0, 0, uiStage);
        coinIcon.loadTexture("assets/coin-icon.png");

        BaseActor arrowIcon = new BaseActor(0, 0, uiStage);
        arrowIcon.loadTexture("assets/arrow-icon.png");


        uiTable.pad(20);
        uiTable.add(healthIcon);
        uiTable.add(healthLabel);
        uiTable.add().expandX();
        uiTable.add(coinIcon);
        uiTable.add(coinLabel);
        uiTable.add().expandX();
        uiTable.add(arrowIcon);
        uiTable.add(arrowLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(8).expandX().expandY();
        uiTable.row();
        uiTable.add(dialogBox).colspan(8);

        for (MapObject obj : tma.getTileList("Coin")) {
            MapProperties props = obj.getProperties();
            new Coin((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        MapObject treasureTile = tma.getTileList("Treasure").get(0);
        MapProperties treasureProps = treasureTile.getProperties();

        treasure = new Treasure((float) treasureProps.get("x"), (float) treasureProps.get("y"), mainStage);

        for (MapObject obj : tma.getTileList("Flyer")) {
            MapProperties props = obj.getProperties();
            new Flyer((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getTileList("NPC")) {
            MapProperties props = obj.getProperties();
            NPC s = new NPC((float) props.get("x"), (float) props.get("y"), mainStage);
            s.setID((String) props.get("id"));
            s.setText((String) props.get("text"));
        }

        MapObject shopHeartTile = tma.getTileList("ShopHeart").get(0);
        MapProperties shopHeartProps = shopHeartTile.getProperties();
        shopHeart = new ShopHeart((float) shopHeartProps.get("x"), (float) shopHeartProps.get("y"), mainStage);

        MapObject shopArrowTile = tma.getTileList("ShopArrow").get(0);
        MapProperties shopArrowProps = shopArrowTile.getProperties();
        shopArrow = new ShopArrow((float) shopArrowProps.get("x"), (float) shopArrowProps.get("y"), mainStage);
    }

    public void update(float dt) {
        if (gameOver) return;

        healthLabel.setText(" x " + health);
        coinLabel.setText(" x " + coins);
        arrowLabel.setText(" x " + arrows);

        if (!sword.isVisible()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                hero.accelerateAtAngle(180);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                hero.accelerateAtAngle(0);
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                hero.accelerateAtAngle(90);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                hero.accelerateAtAngle(270);
        }

        for (BaseActor solid : BaseActor.getList(mainStage, "com.mygdx.game.Solid")) {
            hero.preventOverlap(solid);

            for (BaseActor flyer : BaseActor.getList(mainStage, "com.mygdx.game.Flyer")) {
                if (flyer.overlaps(solid)) {
                    flyer.preventOverlap(solid);
                    flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                }
            }
        }

        if (sword.isVisible()) {
            for (BaseActor brush : BaseActor.getList(mainStage, "com.mygdx.game.Brush")) {
                if (sword.overlaps(brush)) {
                    brush.remove();
                }
            }

            for (BaseActor flyer : BaseActor.getList(mainStage, "com.mygdx.game.Flyer")) {
                if (sword.overlaps(flyer)) {
                    flyer.remove();
                    Coin coin = new Coin(0, 0, mainStage);
                    coin.centerAtActor(flyer);
                    Smoke smoke = new Smoke(0, 0, mainStage);
                    smoke.centerAtActor(flyer);
                }
            }
        }

        for (BaseActor coin : BaseActor.getList(mainStage, "com.mygdx.game.Coin")) {
            if (hero.overlaps(coin)) {
                coin.remove();
                coins++;
            }
        }

        if (hero.overlaps(treasure)) {
            messageLabel.setText("You win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setFontScale(2);
            messageLabel.setVisible(true);
            treasure.remove();
            gameOver = true;
        }

        if (health <= 0) {
            messageLabel.setText("Game over...");
            messageLabel.setColor(Color.RED);
            messageLabel.setFontScale(2);
            messageLabel.setVisible(true);
            hero.remove();
            gameOver = true;
        }

        for (BaseActor flyer : BaseActor.getList(mainStage, "com.mygdx.game.Flyer")) {
            if (hero.overlaps(flyer)) {
                hero.preventOverlap(flyer);
                flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                Vector2 heroPosition = new Vector2(hero.getX(), hero.getY());
                Vector2 flyerPosition = new Vector2(flyer.getX(), flyer.getY());
                Vector2 hitVector = heroPosition.sub(flyerPosition);

                hero.setMotionAngle(hitVector.angle());
                hero.setSpeed(100);
                health--;
            }
        }

        for (BaseActor arrow : BaseActor.getList(mainStage, "com.mygdx.game.Arrow")) {
            for (BaseActor flyer : BaseActor.getList(mainStage, "com.mygdx.game.Flyer")) {
                if (arrow.overlaps(flyer)) {
                    flyer.remove();
                    arrow.remove();
                    Coin coin = new Coin(0, 0, mainStage);
                    coin.centerAtActor(flyer);
                    Smoke smoke = new Smoke(0, 0, mainStage);
                    smoke.centerAtActor(flyer);
                }
            }

            for (BaseActor solid : BaseActor.getList(mainStage, "com.mygdx.game.Solid")) {
                if (arrow.overlaps(solid)) {
                    arrow.preventOverlap(solid);
                    arrow.setSpeed(0);
                    arrow.addAction(Actions.fadeIn(0.5f));
                    arrow.addAction(Actions.after(Actions.removeActor()));
                }
            }
        }

        for (BaseActor npcActor : BaseActor.getList(mainStage, "com.mygdx.game.NPC")) {
            NPC npc = (NPC) npcActor;
            hero.preventOverlap(npc);
            boolean nearby = hero.isWithinDistance(4, npc);

            if (nearby && !npc.isViewing()) {
                // check npc id for dynamic text
                if (npc.getID().equals("Gatekeeper")) {
                    int flyerCount = BaseActor.count(mainStage, "com.mygdx.game.Flyer");
                    String message = "Destroy the flyers and you can have the treasure. ";
                    if (flyerCount > 1)
                        message += "There are " + flyerCount + " left.";
                    else if (flyerCount == 1)
                        message += "There is " + flyerCount + " left.";
                    else // flyerCount == 0
                    {
                        message += "It is yours!";
                        npc.addAction(Actions.fadeOut(5.0f));
                        npc.addAction(Actions.after(Actions.moveBy(-10000, -10000)));
                    }

                    dialogBox.setText(message);
                } else {
                    dialogBox.setText(npc.getText());
                }
                dialogBox.setVisible(true);
                npc.setViewing(true);
            }

            if (npc.isViewing() && !nearby) {
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                npc.setViewing(false);
            }
        }


    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void swingSword() {
        if (sword.isVisible()) return;

        hero.setSpeed(0);

        float facingAngle = hero.getFacingAngle();

        Vector2 offset = new Vector2();
        if (facingAngle == 0)
            offset.set(0.50f, 0.20f);
        else if (facingAngle == 90)
            offset.set(0.65f, 0.50f);
        else if (facingAngle == 180)
            offset.set(0.40f, 0.20f);
        else
            offset.set(0.25f, 0.20f);

        sword.setPosition(hero.getX(), hero.getY());
        sword.moveBy(offset.x * hero.getWidth(), offset.y * hero.getHeight());

        float swordArc = 90;
        sword.setRotation(facingAngle - swordArc / 2);
        sword.setOriginX(0);
        sword.setVisible(true);
        sword.addAction(Actions.rotateBy(swordArc, 0.25f));
        sword.addAction(Actions.after(Actions.visible(false)));

        if (facingAngle == 90 || facingAngle == 180)
            hero.toFront();
        else
            sword.toFront();
    }

    public void shootArrow() {
        if (arrows <= 0) return;

        arrows--;

        Arrow arrow = new Arrow(0, 0, mainStage);
        arrow.centerAtActor(hero);
        arrow.setRotation(hero.getFacingAngle());
        arrow.setMotionAngle(hero.getMotionAngle());
    }

    @Override
    public boolean keyDown(int keycode) {
        if (gameOver) return false;

        if (keycode == Input.Keys.S) {
            swingSword();
        }

        if (keycode == Input.Keys.A) {
            shootArrow();
        }

        if (keycode == Input.Keys.B) {
            if (hero.overlaps(shopHeart) && coins >= 3) {
                coins -= 3;
                health += 1;
            }

            if (hero.overlaps(shopArrow) && coins >= 4) {
                coins -= 4;
                arrows += 3;
            }
        }

        return false;
    }
}