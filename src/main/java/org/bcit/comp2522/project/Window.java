package org.bcit.comp2522.project;

import processing.core.PApplet;


public class Window extends PApplet {

  private StartMenu startMenu;

  public void settings() {
    size(960, 540);
  }

  public void setup() {
    startMenu = new StartMenu(this);
  }

  public void draw() {
    startMenu.draw();
  }

  public void mousePressed() {
    startMenu.mousePressed();
  }

  public static void main(String[] args) {
    String[] processingArgs = {"Window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);
  }

  public void startGame() {
    //TODO: Implement this method to start the game
  }

  public void openOptions() {
    //TODO: Implement this method to open the Options menu
  }

  public void exitGame() {
    exit();
  }
}
