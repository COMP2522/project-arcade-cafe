package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.util.ArrayList;

public class StartMenu {

  private PApplet pApplet;
  private ArrayList<Button> buttons;

  public StartMenu(PApplet pApplet) {
    this.pApplet = pApplet;
    buttons = new ArrayList<>();
    addButton("Start", pApplet.width / 2, pApplet.height / 2 - 50, 100, 50, 20, 0xFFFFFFFF);
    addButton("How to Play", pApplet.width / 2, pApplet.height / 2 + 50, 100, 50, 20, 0xFFFFFFFF);
    addButton("Exit", pApplet.width / 2, pApplet.height / 2 + 150, 100, 50, 20, 0xFFFFFFFF);
  }

  public void draw() {
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

  private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour) {
    buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour));
  }
}

