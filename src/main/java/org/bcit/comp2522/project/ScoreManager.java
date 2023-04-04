package org.bcit.comp2522.project;

import processing.core.PApplet;

public class ScoreManager {
    private static ScoreManager singleton;
    private int score;
    private PApplet window;


    private ScoreManager(PApplet window) {
        this.score = 0;
        this.window = window;
    }

    public static ScoreManager getInstance(PApplet window) {
        if(singleton == null) {
            singleton = new ScoreManager(window);
        }
        return singleton;
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }

    public void draw() {
        window.textSize(32);
        window.fill(255);
        window.text("Score: " + score, 60, 60);
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }
}
