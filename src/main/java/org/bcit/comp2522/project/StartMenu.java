package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.function.Consumer;
import processing.core.PImage;

public class StartMenu extends PApplet{

  private PApplet pApplet;
  private ArrayList<Button> buttons;
  private boolean buttonsInitialized = false;
  private Consumer<Integer> onStateChange;

  private Button goBackButton;



  // MENU SETUP //

  public StartMenu(PApplet pApplet, Consumer<Integer> onStateChange) {
    this.pApplet = pApplet;
    buttons = new ArrayList<>();
    this.onStateChange = onStateChange;
  }

  public void draw() {
    if (!buttonsInitialized) {
      addButton("Start", pApplet.width / 2, pApplet.height / 2 - 50, 100, 50, 20, 0xFFFFFFFF, this::startGame);
      addButton("Scoreboard", pApplet.width / 2, pApplet.height / 2 + 50, 100, 50, 20, 0xFFFFFFFF, this::openScoreboard);
      addButton("Exit", pApplet.width / 2, pApplet.height / 2 + 150, 100, 50, 20, 0xFFFFFFFF, this::exitGame);
      buttonsInitialized = true;
    }

    pApplet.background(0);
    for (Button button : buttons) {
      button.draw(pApplet);
    }
  }

  // BUTTON INTERACTION FUNCTIONS //
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
        button.onClick();
      }
    }
  }

  private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour, Runnable onClickAction) {
    buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
  }

  public void startGame() {
    //TODO: Implement this method to start the game
    if (onStateChange != null) {
      onStateChange.accept(1); // Set the state to 1
    }
  }

  public void openScoreboard() {
    if (onStateChange != null) {
      onStateChange.accept(2); // Set the state to 2 for the Scoreboard
    }
  }

  public void exitGame() {
    pApplet.exit();
  }

  public void drawScoreboard() {
    // TODO: Add drawing code for the scoreboard

    if (goBackButton == null) {
      goBackButton = new Button("Go Back", pApplet.width / 2, pApplet.height - 50, 100, 50, 20, 0xFFFFFFFF, this::goBackToMainMenu);
    }

    goBackButton.draw(pApplet);
  }

  public void goBackToMainMenu() {
    if (onStateChange != null) {
      onStateChange.accept(0); // Set the state to 0 (main menu)
    }
  }

  public void mousePressedScoreboard() {
    if (goBackButton != null && goBackButton.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
      goBackButton.onClick();
    }
  }
}

