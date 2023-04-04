package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.function.Consumer;

public class StartMenu extends PApplet{

  private PApplet pApplet;
  private ArrayList<Button> buttons;
  private boolean buttonsInitialized = false;
  private Consumer<GameState> onStateChange;

  private Button goBackButton;

  private GameState gameState;
  PImage backgroundImage;

  DatabaseHandler db;


  // MENU SETUP //
  public StartMenu(PApplet pApplet,Consumer<GameState> onStateChange) {
    this.pApplet = pApplet;
    this.onStateChange = onStateChange;
    buttons = new ArrayList<>();
    backgroundImage = pApplet.loadImage("src/bgImg/galagaSpace.png"); // Load the background image
  }

  public void draw() {
    if (!buttonsInitialized) {
      addButton("Start", pApplet.width / 2, pApplet.height / 2 + 25, 150, 50, 20, 0xFFFFFFFF, this::startGame);
      addButton("Scoreboard", pApplet.width / 2, pApplet.height / 2 + 100, 150, 50, 20, 0xFFFFFFFF, this::openScoreboard);
      addButton("Exit", pApplet.width / 2, pApplet.height / 2 + 175, 150, 50, 20, 0xFFFFFFFF, this::exitGame);
      buttonsInitialized = true;
    }

    pApplet.image(backgroundImage, 0, 0, pApplet.width, pApplet.height); // Use the loaded backgroundImage

    for (Button button : buttons) {
      button.draw(pApplet);
    }
  }

  // BUTTON INTERACTION FUNCTIONS //
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
        String buttonLabel = button.getLabel();
        if (buttonLabel.equals("Start")) {
          onStateChange.accept(GameState.PLAYING); // Update the gameState to PLAYING
        } else if (buttonLabel.equals("Scoreboard")) {
          onStateChange.accept(GameState.SCORE_BOARD);
        } else if (buttonLabel.equals("Exit")) {
          System.exit(0);
        }
        button.onClick();
        System.out.println("start menu button clicked");
      }
    }
  }

  private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour, Runnable onClickAction) {
    buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
  }

  public void startGame() {
    onStateChange.accept(GameState.PLAYING);
  }

  public void openScoreboard() {
    onStateChange.accept(GameState.SCORE_BOARD);
  }

  public void exitGame() {
    pApplet.exit();
  }
}

