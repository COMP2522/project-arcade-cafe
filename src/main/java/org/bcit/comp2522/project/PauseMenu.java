package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PConstants;
import java.io.FileNotFoundException;

/**
 PauseMenu is a class representing the pause menu for the game.
 It extends the PApplet class from the Processing library, which provides the functionality
 for drawing on the screen and handling user input.
 */
public class PauseMenu extends PApplet{

  private final float MENU_OPACITY = 127.5f; // 50% opacity
  private final float BUTTON_WIDTH = 150;
  private final float BUTTON_HEIGHT = 50;
  private final float BUTTON_MARGIN = 75;
  private final int BUTTON_FONT_SIZE = 20;
  private final int BUTTON_FONT_COLOUR = 255;
  private final int DIVISION = 2;
  private PApplet pApplet;
  private Button resumeButton;
  private Button quitButton;

  /**
   Constructs a new PauseMenu object with the given PApplet instance.
   The pause menu consists of a transparent background and two buttons: "Resume" and "Quit".
   @param pApplet the PApplet instance to draw on.
   */
  public PauseMenu(PApplet pApplet) {
    this.pApplet = pApplet;

    int centerX = pApplet.width / DIVISION;
    int centerY = pApplet.height / DIVISION;

    // Create the resume button centered horizontally, and positioned just below the vertical center of the screen.
    float resumeButtonX = centerX;
    float resumeButtonY = centerY;
    resumeButton = new Button("Resume", resumeButtonX, resumeButtonY,
        BUTTON_WIDTH, BUTTON_HEIGHT,
        BUTTON_FONT_SIZE, BUTTON_FONT_COLOUR,
        () -> {
          LevelManager.getInstance().pause();
        });

    // Create the quit button centered horizontally, and positioned just above the vertical center of the screen.
    float quitButtonX = centerX;
    float quitButtonY = centerY + BUTTON_MARGIN;
    quitButton = new Button("Quit", quitButtonX, quitButtonY,
        BUTTON_WIDTH, BUTTON_HEIGHT,
        BUTTON_FONT_SIZE, BUTTON_FONT_COLOUR,
        () -> {
          try{
            LevelManager.getInstance().writeToFile("save.json");
          }
          catch (FileNotFoundException o) {
            throw new RuntimeException(o);
          }
          System.exit(0);
        });
  }

  /**
   Draws the pause menu on the screen.
   This method is called every frame by the Processing engine.
   */
  public void draw() {
    // Draw the transparent background
    pApplet.pushStyle();
    pApplet.fill(0, MENU_OPACITY);
    pApplet.rectMode(PConstants.CORNER);
    pApplet.rect(0, 0, pApplet.width, pApplet.height);
    pApplet.popStyle();

    // Draw the buttons
    resumeButton.draw(pApplet);
    quitButton.draw(pApplet);
  }

  /**
   Checks if the given coordinates are over either of the pause menu buttons.
   @param mouseX the x-coordinate of the mouse.
   @param mouseY the y-coordinate of the mouse.
   @return true if the mouse is over either of the buttons, false otherwise.
   */
  public boolean isMouseOver(float mouseX, float mouseY) {
    return resumeButton.isMouseOver(mouseX, mouseY) || quitButton.isMouseOver(mouseX, mouseY);
  }

  /**
   Handles mouse click events on the pause menu buttons.
   This method is called by the Processing engine whenever the user clicks the mouse.
   */
  public void onMousePressed() {
    if (resumeButton.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
      resumeButton.onClick();
    } else if (quitButton.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
      quitButton.onClick();
    }
  }
}
