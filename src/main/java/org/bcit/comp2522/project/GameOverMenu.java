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

    int state;
    private final Consumer<Integer> onStateChange;

    public GameOverMenu(PApplet pApplet, Consumer<Integer> onStateChange) {
        this.pApplet = pApplet;
        this.onStateChange = onStateChange;
        this.buttonWidth = 150;
        this.buttonHeight = 50;
        this.buttonSpacing = 20;
        startX = pApplet.width / 2 - buttonWidth / 2;
        startY = pApplet.height / 2 + 50;
        this.state = 0;
        addButton("Main Menu", startX, startY, buttonWidth, buttonHeight, 20, 0xFFFFFFFF, this::goBackToMainMenu);
    }
    public void setState() {
        if (onStateChange != null) {
            onStateChange.accept(3);
        }
    }

    public void draw() {
        pApplet.background(0);
        pApplet.textSize(32);
        pApplet.fill(255);
        pApplet.textAlign(PApplet.CENTER);
        pApplet.text("GAME OVER", pApplet.width / 2, pApplet.height / 2);

        for (Button button : buttons) {
            button.draw(pApplet);
        }
    }

    public void goBackToMainMenu() {
        if (onStateChange != null) {
            onStateChange.accept(0); // Set the state to 0 (main menu)
        }
        this.state = 0; // Set the state to 0 in the Window class
    }

    public void mousePressed() {
        System.out.println("mouse clicked");
        if (isButtonClicked(startX, startY, buttonWidth, buttonHeight)) {
            goBackToMainMenu();
        }
    }


    private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour, Runnable onClickAction) {
        if (buttons == null) {
            buttons = new ArrayList<>();
        }
        buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
    }

    private boolean isButtonClicked(int x, int y, int width, int height) {
        System.out.println("isButtonClicked method called");
        return (pApplet.mouseX >= x && pApplet.mouseX <= x + width &&
                pApplet.mouseY >= y && pApplet.mouseY <= y + height);
    }
}
