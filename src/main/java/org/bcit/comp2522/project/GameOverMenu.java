package org.bcit.comp2522.project;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.function.Consumer;

public class GameOverMenu {

    private PApplet pApplet;
    private ArrayList<Button> buttons;
    private final int buttonWidth;
    private final int buttonHeight;
    private final int buttonSpacing;
    private final int startX;
    private final int startY;
    private final Consumer<GameState> onStateChange;
    private ScoreManager scoreManager;

    public GameOverMenu(PApplet pApplet, Consumer<GameState> onStateChange) {
        this.pApplet = pApplet;
        this.onStateChange = onStateChange;
        this.buttonWidth = 150;
        this.buttonHeight = 50;
        this.buttonSpacing = 20;
        startX = pApplet.width / 2;
        startY = pApplet.height / 2 + 50;
        scoreManager = ScoreManager.getInstance(pApplet);
        addButton("Main Menu", startX, startY, buttonWidth, buttonHeight, 20, 0xFFFFFFFF, this::goBackToMainMenu);
    }

    public void draw() {
        pApplet.background(0);
        pApplet.textSize(32);
        pApplet.fill(255);
        pApplet.textAlign(PApplet.CENTER);
        pApplet.text("GAME OVER", pApplet.width / 2, pApplet.height / 2 - 50);

        // Retrieve the actual score value from the ScoreManager instance
        int score = scoreManager.getScore();
        System.out.println(score);
        pApplet.text("Score: " + score, pApplet.width/2, pApplet.height/2);

        for (Button button : buttons) {
            button.draw(pApplet);
        }
    }

    public void mousePressed() {
        for (Button button : buttons) {
            if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
                button.onClick();
                System.out.println("Go back main menu button clicked");
            }
        }
    }

    public void goBackToMainMenu() {
        LevelManager.getInstance().resetGameOver();
        LevelManager.getInstance().setState(GameState.MAIN_MENU);
        LevelManager.getInstance().resetGame();
    }


    private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour, Runnable onClickAction) {
        if (buttons == null) {
            buttons = new ArrayList<>();
        }
        buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
    }
}
