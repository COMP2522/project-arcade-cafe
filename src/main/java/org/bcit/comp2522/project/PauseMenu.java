package org.bcit.comp2522.project;

import java.io.File;
import java.io.FileNotFoundException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * PauseMenu is a class representing the pause menu for the game.
 * It extends the PApplet class from the Processing library, which provides the functionality
 * for drawing on the screen and handling user input.
 *
 * @author Helen Liu
 * @author Samuel Chua
 *
 */
public class PauseMenu extends PApplet {

  private final float menuOpacity = 127.5f; // 50% opacity
  private final float buttonWidth = 150;
  private final float buttonHeight = 50;
  private final float buttonMargin = 75;
  private final int fontSize = 20;
  private final int fontColour = 255;
  private final int division = 2;
  private PApplet papplet;
  private Button resumeButton;
  private Button quitButton;
  private boolean soundPlayed = false;

  /**
   * Constructs a new PauseMenu object with the given PApplet instance.
   * The pause menu consiste of a transparent background and two buttons: "Resume" and "Quit".
   *
   * @param papplet the PApplet instance to draw on.
   */
  public PauseMenu(PApplet papplet) {
    this.papplet = papplet;
    int centerX = papplet.width / division;
    int centerY = papplet.height / division;

    // Create the resume button centered horizontally, and positioned just
    // below the vertical center of the screen.
    float resumeButtonX = centerX;
    float resumeButtonY = centerY;
    resumeButton = new Button("Resume", resumeButtonX, resumeButtonY,
        buttonWidth, buttonHeight,
        fontSize, fontColour,
        () -> {
          LevelManager.getInstance().pause();
        });

    // Create the quit button centered horizontally, and positioned just
    // above the vertical center of the screen.
    float quitButtonX = centerX;
    float quitButtonY = centerY + buttonMargin;
    quitButton = new Button("Save & Quit", quitButtonX, quitButtonY,
        buttonWidth, buttonHeight,
        fontSize, fontColour,
        () -> {
          try {
            LevelManager.getInstance().writeToFile("save.json");
          } catch (FileNotFoundException o) {
            throw new RuntimeException(o);
          }
          System.exit(0);
        });
  }

  /**
   * This method allows audio to play various audio specified by the file.
   *
   * @param soundFilePath specifies what is indicated through the filepath to play audio.
   */
  public static void playSound(String soundFilePath) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                                            new File(soundFilePath).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (Exception ex) {
      System.out.println("Error playing sound.");
      ex.printStackTrace();
    }
  }

  /**
   * Draws the pause menu on the screen.
   * This method is called every frame by the Processing engine.
   */
  public void draw() {
    // Draw the transparent background
    papplet.pushStyle();
    papplet.fill(0, menuOpacity);
    papplet.rectMode(PConstants.CORNER);
    papplet.rect(0, 0, papplet.width, papplet.height);
    papplet.popStyle();

    // Draw the buttons
    resumeButton.draw(papplet);
    quitButton.draw(papplet);
  }

  /**
   * Handles mouse click events on the pause menu buttons.
   * This method is called by the Processing engine whenever the user clicks the mouse.
   */
  public void onMousePressed() {
    if (resumeButton.isMouseOver(papplet.mouseX, papplet.mouseY)) {
      playSound("src/sfx/ui_select.wav");
      resumeButton.onClick();
    } else if (quitButton.isMouseOver(papplet.mouseX, papplet.mouseY)) {
      playSound("src/sfx/ui_select.wav");
      quitButton.onClick();
    }
  }
}
