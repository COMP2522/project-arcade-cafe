package org.bcit.comp2522.project;

import java.awt.*;

public class PowerUp extends Sprite {

    private int dy;
    private String type;
    public int hpCount;
    public int fireCount;

    public PowerUp(int xPos, int yPos, int size, Color color, Window window, int dy) {
        super(xPos, yPos, size, color, window);

    }

    public void increaseHP() {
        hpCount++;
    }

    public void increaseFireRate() {
        fireCount++;
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
    }

    @Override
    public void draw() {
        super.draw();
    }

    public void update() {
        // Move the powerup vertically
        move(0, dy);
    }

}
