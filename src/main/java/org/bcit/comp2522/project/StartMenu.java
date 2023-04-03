package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class StartMenu extends PApplet{

  private PApplet pApplet;
  private ArrayList<Button> buttons;
  private boolean buttonsInitialized = false;
  private Consumer<Integer> onStateChange;

  private Button goBackButton;

  PImage backgroundImage;

  DatabaseHandler db;


  // MENU SETUP //
  public StartMenu(PApplet pApplet) {
    this.pApplet = pApplet;
    buttons = new ArrayList<>();
  }

  public void draw() {
    if (!buttonsInitialized) {
      addButton("Start", pApplet.width / 2, pApplet.height / 2 + 25, 150, 50, 20, 0xFFFFFFFF, this::startGame);
      addButton("Scoreboard", pApplet.width / 2, pApplet.height / 2 + 100, 150, 50, 20, 0xFFFFFFFF, this::openScoreboard);
      addButton("Exit", pApplet.width / 2, pApplet.height / 2 + 175, 150, 50, 20, 0xFFFFFFFF, this::exitGame);
      buttonsInitialized = true;
    }

    PImage background = pApplet.loadImage("src/bgImg/galagaSpace.png");
    pApplet.image(background, 0, 0, pApplet.width, pApplet.height);

    for (Button button : buttons) {
      button.draw(pApplet);
    }
  }

  // BUTTON INTERACTION FUNCTIONS //
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
        button.onClick();
        System.out.println("start menu buttion clicked ");
      }
    }
  }

  private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour, Runnable onClickAction) {
    buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
  }

  public void startGame() {
    LevelManager.getInstance().resetGame();
    LevelManager.getInstance().setState(1);
  }

  public void openScoreboard() {
    LevelManager.getInstance().setState(2); // Set the state to 0 (main menu)
  }

  public void exitGame() {
    pApplet.exit();
  }
}

