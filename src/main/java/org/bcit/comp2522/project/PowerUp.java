package org.bcit.comp2522.project;

import java.awt.*;
import processing.core.PImage;

public class PowerUp extends Sprite {

    private String type;
    public int hpCount;
    public int fireCount;
    private PImage hpImage;

    private PImage fireRateImage;

    public PowerUp(int xPos, int yPos, int size, Color color, Window window, String type) {
        super(xPos, yPos, size, color, window);
        this.type = type;
        hpImage = window.loadImage("src/img/healthPowerUp.png");
        hpImage.resize(30,30);
        fireRateImage = window.loadImage("src/img/cherryPowerUp.png");
        fireRateImage.resize(30,30);

    }

    public void increaseHP() {
        hpCount++;
    }

    public void increaseFireRate() {
        fireCount++;
    }

    public String getType() {
        return type;
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
    }

    @Override
    public void draw() {
        window.pushMatrix();
        window.translate(getX(), getY());
        if (type.equals("hp")) {
            window.image(hpImage, -30 / 2, -30 / 2, 30, 30);
        } else if (type.equals("fireRate")) {
            window.image(fireRateImage,-30 / 2, -30 / 2, 30, 30);
        }
        window.popMatrix();
    }

    public void update() {
        // Move the powerup vertically
        move(0, 2);
    }


}