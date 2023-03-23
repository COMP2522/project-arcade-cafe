package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.function.Consumer;

public class StartMenu extends PApplet{

  private PApplet pApplet;
  private ArrayList<Button> buttons;
  private boolean buttonsInitialized = false;
  private Consumer<Integer> onStateChange;

  public StartMenu(PApplet pApplet, Consumer<Integer> onStateChange) {
    this.pApplet = pApplet;
    buttons = new ArrayList<>();
    this.onStateChange = onStateChange;
  }

  public void draw() {
    if (!buttonsInitialized) {
      addButton("Start", pApplet.width / 2, pApplet.height / 2 - 50, 100, 50, 20, 0xFFFFFFFF, this::startGame);
      addButton("How to Play", pApplet.width / 2, pApplet.height / 2 + 50, 100, 50, 20, 0xFFFFFFFF, this::openOptions);
      addButton("Exit", pApplet.width / 2, pApplet.height / 2 + 150, 100, 50, 20, 0xFFFFFFFF, this::exitGame);
      buttonsInitialized = true;
    }

    pApplet.background(255);
    for (Button button : buttons) {
      button.draw(pApplet);
    }
  }


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

  public void openOptions() {
    //TODO: Implement this method to open the Options menu
    System.out.println("clicked");
  }

  public void exitGame() {
    pApplet.exit();
//    System.out.println("Exiting game");
  }
}

