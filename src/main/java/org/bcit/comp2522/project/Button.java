package org.bcit.comp2522.project;

import processing.core.PApplet;

public class Button {

  private String label;
  private float xcoordinate;
  private float ycooridnate;
  private float width;
  private float height;
  private int fontSize;
  private int fontColour;
  private Runnable onClickAction;
  private static final int BUTTON_COLOR = 100;

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
    this.ycooridnate = buttonYcoordinate;
    this.width = buttonWidth;
    this.height = buttonHeight;
    this.fontSize = buttonFontSize;
    this.fontColour = buttonFontColour;
    this.onClickAction = buttonOnClickAction;
  }

  public void draw(final PApplet papplet) {
    papplet.rectMode(PApplet.CENTER);
    papplet.fill(BUTTON_COLOR);
    papplet.rect(xcoordinate, ycooridnate, width, height);
    papplet.fill(fontColour);
    papplet.textSize(fontSize);
    papplet.textAlign(PApplet.CENTER, PApplet.CENTER);
    papplet.text(label, xcoordinate, ycooridnate);
  }

  public boolean isMouseOver(final float mouseX, final float mouseY) {
    return mouseX > xcoordinate - width / 2
            && mouseX < xcoordinate + width / 2
            && mouseY > ycooridnate - height / 2
            && mouseY < ycooridnate + height / 2;
  }

  public void onClick() {
    if (onClickAction != null) {
      onClickAction.run();
    }
  }

  public String getLabel() {
    return this.label;
  }
}
