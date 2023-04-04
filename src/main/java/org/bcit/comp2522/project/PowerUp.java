package org.bcit.comp2522.project;

import java.awt.*;
import processing.core.PImage;

/**
 * The PowerUp class represents a power-up object in the game.
 * A power-up can have different types, such as "hp" (health) or "fireRate" (fire rate).
 * The power-up object moves vertically and is drawn with a corresponding image.
 *
 * @author Eric Cho, Samuel Chua, Helen Liu, Mina Park, Mylo Yu
 * @version 2023-04-03
 */
public class PowerUp extends Sprite {

    /**
     * Constant for image size for power up.
     */
    private static final int POWERUP_IMAGE_SIZE = 30;

    /**
     * The type of the power-up. Possible values are "hp" (health) and "fireRate" (fire rate).
     */
    private String type;

    /**
     * The count of health points for the power-up.
     */
    public int hpCount;

    /**
     * The count of fire rate points for the power-up.
     */
    public int fireCount;

    /**
     * The image of the health power-up.
     */
    private PImage hpImage;

    /**
     * The image of the fire rate power-up.
     */
    private PImage fireRateImage;

    /**
     * Constructs a new PowerUp object with the specified properties.
     *
     * @param xPos   The x position of the power-up.
     * @param yPos   The y position of the power-up.
     * @param size   The size of the power-up.
     * @param window The window in which the power-up will be rendered.
     * @param type   The type of the power-up, either "hp" or "fireRate".
     */
    public PowerUp(int xPos, int yPos, int size, Window window, String type) {
        super(xPos, yPos, size, window);
        this.type = type;
        hpImage = window.loadImage("src/img/hpPowerUp.png");
        hpImage.resize(POWERUP_IMAGE_SIZE,POWERUP_IMAGE_SIZE);
        fireRateImage = window.loadImage("src/img/frPowerUp.png");
        fireRateImage.resize(POWERUP_IMAGE_SIZE,POWERUP_IMAGE_SIZE);
    }

    /**
     * Returns the type of the power-up.
     *
     * @return The type of the power-up, either "hp" or "fireRate".
     */
    public String getType() {
        return type;
    }

    /**
     * Moves the power-up by the specified x and y amounts.
     *
     * @param dx The amount to move the power-up in the x direction.
     * @param dy The amount to move the power-up in the y direction.
     */
    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
    }

    /**
     * Draws the power-up at its current position with the corresponding image
     * based on its type.
     */
    @Override
    public void draw() {
        window.pushMatrix();
        window.translate(getX(), getY());
        if (type.equals("hp")) {
            window.image(hpImage,-POWERUP_IMAGE_SIZE / 2, -POWERUP_IMAGE_SIZE / 2,
                        POWERUP_IMAGE_SIZE, POWERUP_IMAGE_SIZE);
        } else if (type.equals("fireRate")) {
            window.image(fireRateImage,-POWERUP_IMAGE_SIZE / 2, -POWERUP_IMAGE_SIZE / 2,
                        POWERUP_IMAGE_SIZE, POWERUP_IMAGE_SIZE);
        }
        window.popMatrix();
    }

    /**
     * Updates the power-up's position, moving it vertically.
     */
    public void update() {
        // Move the powerup vertically
        move(0, 2);
    }

}