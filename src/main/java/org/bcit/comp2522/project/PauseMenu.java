package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PConstants;

import java.io.FileNotFoundException;

public class PauseMenu extends PApplet{

//  private boolean visible = false;
  private final float MENU_OPACITY = 127.5f; // 50% opacity
  private final float BUTTON_WIDTH = 120;
  private final float BUTTON_HEIGHT = 60;
  private final float BUTTON_MARGIN = 20;
  private final int BUTTON_FONT_SIZE = 24;
  private final int BUTTON_FONT_COLOUR = 255;
  private final int RESUME_BUTTON_COLOUR = 0xFF66CC66; // light green
  private final int QUIT_BUTTON_COLOUR = 0xFFFF0000; // red

  private PApplet pApplet;
  private Button resumeButton;
  private Button quitButton;

  public PauseMenu(PApplet pApplet) {
    this.pApplet = pApplet;

    float centerX = pApplet.width / 2.0f;
    float centerY = pApplet.height / 2.0f;

    // Create the resume button centered horizontally, and positioned
    // just below the vertical center of the screen.
    float resumeButtonX = centerX - (BUTTON_WIDTH / 2.0f);
    float resumeButtonY = centerY + (BUTTON_MARGIN / 2.0f);
    resumeButton = new Button("Resume", resumeButtonX, resumeButtonY,
        BUTTON_WIDTH, BUTTON_HEIGHT,
        BUTTON_FONT_SIZE, BUTTON_FONT_COLOUR,
        () -> {
          LevelManager.getInstance().pause();
        });

    // Create the quit button centered horizontally, and positioned
    // just above the vertical center of the screen.
    float quitButtonX = centerX - (BUTTON_WIDTH / 2.0f);
    float quitButtonY = centerY - (BUTTON_HEIGHT / 2.0f) - (BUTTON_MARGIN / 2.0f);
    quitButton = new Button("Quit", quitButtonX, quitButtonY,
        BUTTON_WIDTH, BUTTON_HEIGHT,
        BUTTON_FONT_SIZE, BUTTON_FONT_COLOUR,
        () -> {
          try{
            LevelManager.getInstance().writeToFile("src/data/save.json");
          }
          catch (FileNotFoundException o) {
            throw new RuntimeException(o);
          }
          System.exit(0);
        });
//    quitButton.setBackgroundColor(QUIT_BUTTON_COLOUR);
  }

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

//  public void hide() {
//    visible = false;
//  }
//
//  public boolean isVisible() {
//    return visible;
//  }

  public boolean isMouseOver(float mouseX, float mouseY) {
    return resumeButton.isMouseOver(mouseX, mouseY) || quitButton.isMouseOver(mouseX, mouseY);
  }

  public void onMousePressed() {
    if (resumeButton.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
      resumeButton.onClick();
    } else if (quitButton.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
      quitButton.onClick();
    }
  }
}
