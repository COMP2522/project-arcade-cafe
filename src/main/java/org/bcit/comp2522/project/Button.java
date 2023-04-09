package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.awt.Color;

/**
 * This Button class represents the graphical buttons featured in the menus.
 * The button can be customized with label, position, size, font size, font color,
 * and an onClickAction method that is executed when the button is clicked.
 */
public class Button {

  private String label;
  private float xcoordinate;
  private float ycoordinate;
  private float width;
  private float height;
  private int fontSize;
  private int fontColour;
  private Runnable onClickAction;
  private static final int BUTTON_COLOR = 100;

  /**
   * Constructs a new Button object.
   *
   * @param buttonLabel the label to display on the button
   * @param buttonXcoordinate the x-coordinate of the button's center
   * @param buttonYcoordinate the y-coordinate of the button's center
   * @param buttonWidth the width of the button
   * @param buttonHeight the height of the button
   * @param buttonFontSize the font size of the label
   * @param buttonFontColour the color of the label
   * @param buttonOnClickAction the action to execute when the button is clicked
   */
  public Button(final String buttonLabel,
                final float buttonXcoordinate,
                final float buttonYcoordinate,
                final float buttonWidth,
                final float buttonHeight,
                final int buttonFontSize,
                final int buttonFontColour,
                final Runnable buttonOnClickAction) {
    this.label = buttonLabel;
    this.xcoordinate = buttonXcoordinate;
    this.ycoordinate = buttonYcoordinate;
    this.width = buttonWidth;
    this.height = buttonHeight;
    this.fontSize = buttonFontSize;
    this.fontColour = buttonFontColour;
    this.onClickAction = buttonOnClickAction;
  }

  /**
   * Draws the button on the Processing sketch.
   *
   * @param papplet the PApplet object representing the sketch
   */
  public void draw(final PApplet papplet) {
    papplet.rectMode(PApplet.CENTER);
    papplet.fill(BUTTON_COLOR);
    papplet.rect(xcoordinate, ycoordinate, width, height);
    papplet.fill(fontColour);
    papplet.textSize(fontSize);
    papplet.textAlign(PApplet.CENTER, PApplet.CENTER);
    papplet.text(label, xcoordinate, ycoordinate);
  }

  /**
   * Determines if the given coordinates are over the button.
   *
   * @param mouseX the x-coordinate of the mouse
   * @param mouseY the y-coordinate of the mouse
   * @return true if the coordinates are over the button, false otherwise
   */
  public boolean isMouseOver(final float mouseX, final float mouseY) {
    return mouseX > xcoordinate - width / 2
            && mouseX < xcoordinate + width / 2
            && mouseY > ycoordinate - height / 2
            && mouseY < ycoordinate + height / 2;
  }

  /**
   * Executes the button's onClickAction.
   */
  public void onClick() {
    if (onClickAction != null) {
      onClickAction.run();
    }
  }

  /**
   * Gets the label of the button.
   *
   * @return the button label
   */
  public String getLabel() {
    return this.label;
  }
}
