package org.bcit.comp2522.project;

import processing.core.PImage;

import static processing.core.PApplet.dist;

public class LivesManager {

    private Player player;
    private Window window;
    private PImage heartImage;
    private int heartSize;
    private int heartPadding;
    private int leftPadding;

    public LivesManager(Player player, Window window, int initialHP) {
        this.player = player;
        this.window = window;
        this.heartSize = 30;
        this.heartPadding = 5;
        this.leftPadding = 20;
        heartImage = window.loadImage("src/img/heartLife.png");
        heartImage.resize(heartSize, heartSize);
        player.setHp(initialHP);
    }

    public void draw() {
        for (int i = 0; i < player.getHp(); i++) {
            int xPos = leftPadding + heartPadding + (i * (heartSize + heartPadding));
            int yPos = heartPadding + leftPadding;
            window.image(heartImage, xPos, yPos);
        }
    }

    public void loseLife() {
        if (player.getHp() > 0) {
            player.setHp(player.getHp() - 1);
        }
    }

    public void gainLife() {
        player.setHp(player.getHp() + 1);
    }

    public void resetLives() {
        player.setHp(1); // Set the initial HP to 3 or any other value you prefer
    }

    public int getLives() {
        return player.getHp();
    }

//    public boolean collidesWith(Player player, Enemy enemy) {
//        float distance = dist(player.getX(), player.getY(), enemy.getX(), enemy.getY());
//        float minDistance = (player.getSize() + enemy.getSize()) / 2;
//        if (distance <= minDistance) {
//            loseLife(); // Use the loseLife() method of LivesManager
//            return true;
//        }
//        return false;
//    }

}

