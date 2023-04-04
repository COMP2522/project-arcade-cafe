package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 PauseMenu is a class representing the pause menu for the game.
 It extends the PApplet class from the Processing library, which provides the functionality
 for drawing on the screen and handling user input.
 */
public class PauseMenu extends PApplet{

  private final float MENU_OPACITY = 127.5f; // 50% opacity
  private final float BUTTON_WIDTH = 120;
  private final float BUTTON_HEIGHT = 60;
  private final float BUTTON_MARGIN = 20;
  private final int BUTTON_FONT_SIZE = 24;
  private final int BUTTON_FONT_COLOUR = 255;
  private final float DIVISION = 2.0f;
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

    float centerX = pApplet.width / DIVISION;
    float centerY = pApplet.height / DIVISION;

    // Create the resume button centered horizontally, and positioned just below the vertical center of the screen.
    float resumeButtonX = centerX - (BUTTON_WIDTH / DIVISION);
    float resumeButtonY = centerY + (BUTTON_MARGIN / DIVISION);
    resumeButton = new Button("Resume", resumeButtonX, resumeButtonY,
        BUTTON_WIDTH, BUTTON_HEIGHT,
        BUTTON_FONT_SIZE, BUTTON_FONT_COLOUR,
        () -> {
          LevelManager.getInstance().pause();
        });

    // Create the quit button centered horizontally, and positioned just above the vertical center of the screen.
    float quitButtonX = centerX - (BUTTON_WIDTH / DIVISION);
    float quitButtonY = centerY - (BUTTON_HEIGHT / DIVISION) - (BUTTON_MARGIN / DIVISION);
    quitButton = new Button("Quit", quitButtonX, quitButtonY,
        BUTTON_WIDTH, BUTTON_HEIGHT,
        BUTTON_FONT_SIZE, BUTTON_FONT_COLOUR,
        () -> {
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
