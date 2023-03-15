package org.bcit.comp2522.project;

import processing.core.PApplet;

public class Button {

  private String label;
  private float x;
  private float y;
  private float width;
  private float height;
  private int fontSize;
  private int fontColour;

  public Button(String label, float x, float y, float width, float height, int fontSize, int fontColour) {
    this.label = label;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.fontSize = fontSize;
    this.fontColour = fontColour;
  }

  public void draw(PApplet pApplet) {
    pApplet.rectMode(PApplet.CENTER);
    pApplet.fill(100);
    pApplet.rect(x, y, width, height);
    pApplet.fill(fontColour);
    pApplet.textSize(fontSize);
    pApplet.textAlign(PApplet.CENTER, PApplet.CENTER);
    pApplet.text(label, x, y);
  }

  public boolean isMouseOver(float mouseX, float mouseY) {
    return mouseX > x - width / 2 && mouseX < x + width / 2 && mouseY > y - height / 2 && mouseY < y + height / 2;
  }

  public void onClick() {
    //TODO: Implement this method in StartMenu.java and handle the button click event
  }

}
