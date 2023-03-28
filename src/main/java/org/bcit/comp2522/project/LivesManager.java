package org.bcit.comp2522.project;
import processing.core.PImage;

public class LivesManager {

    private int lives;
    private Window window;
    private PImage heartImage;
    private int heartSize;
    private int heartPadding;
    private int leftPadding;

    public LivesManager(int initialLives, Window window) {
        this.lives = initialLives;
        this.window = window;
        this.heartSize = 30;
        this.heartPadding = 5;
        this.leftPadding = 20;
        heartImage = window.loadImage("src/img/heartLife.png");
        heartImage.resize(heartSize, heartSize);
    }

    public void draw() {
        for (int i = 0; i < lives; i++) {
            int xPos = leftPadding + heartPadding + (i * (heartSize + heartPadding));
            int yPos = heartPadding + leftPadding;
            window.image(heartImage, xPos, yPos);
        }
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public void gainLife() {
        lives++;
    }

    public int getLives() {
        return lives;
    }
}
