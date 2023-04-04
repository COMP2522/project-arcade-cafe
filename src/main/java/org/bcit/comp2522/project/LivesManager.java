package org.bcit.comp2522.project;

import processing.core.PImage;

import java.util.List;

import static processing.core.PApplet.dist;

/**
 * Defines a LivesManager that handles the player's lives in the game.
 * Lives are represented by hearts displayed on the screen using the draw() method.
 *
 *  @author Eric Cho, Samuel Chua, Helen Liu, Mina Park, Mylo Yu
 *  @version 2023-04-03
 */
public class LivesManager {

    private Player player;
    private Window window;

    /**
     * The heart image used to represent a life.
     */
    private PImage heartImage;

    /**
     * The size of the heart image.
     */
    private int heartSize;

    /**
     * The padding between heart images.
     */
    private int heartPadding;

    /**
     * The padding from the left side of the window.
     */
    private int leftPadding;

    /**
     * The maximum number of HP allowed.
     */
    private static final int MAX_HP = 5;

    /**
     * Heart image size.
     */
    private static final int SIZE = 30;

    /**
     * Padding/space between heart images.
     */
    private static final int PADDING = 5;

    /**
     * Leftmost padding between heart image and window.
     */
    private static final int EDGEPADDING = 20;

    /**
     * Constructs a LivesManager object with the specified player, window, and initialHP.
     *
     * @param player The player object.
     * @param window The window object.
     * @param initialHP The initial number of lives for the player.
     */
    public LivesManager(Player player, Window window, int initialHP) {
        this.player = player;
        this.window = window;
        this.heartSize = SIZE;
        this.heartPadding = PADDING;
        this.leftPadding = EDGEPADDING;
        heartImage = window.loadImage("src/img/heartLife.png");
        heartImage.resize(heartSize, heartSize);
        player.setHp(initialHP);
    }

    /**
     * Draws the lives (hearts) on the window.
     */
    public void draw() {
        for (int i = 0; i < player.getHp(); i++) {
            int xPos = leftPadding + heartPadding + (i * (heartSize + heartPadding));
            int yPos = heartPadding + leftPadding;
            window.image(heartImage, xPos, yPos);
        }
    }

    /**
     * Increases the player's lives by 1, up to the maximum HP allowed at a given time.
     */
    public void gainLife() {
        if (player.getHp() < MAX_HP) {
            player.setHp(player.getHp() + 1);
        }
    }

}
