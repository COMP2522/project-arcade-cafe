package org.bcit.comp2522.project;

import processing.core.PApplet;
public class Window extends PApplet{

  public void settings() {
    size(512, 700);
  }
  //TODO: think of a better aspect ratio

  public void setup() {
    //TODO: put setup stuff here
  }

  /**
   * Called on every frame. Updates scene object
   * state and redraws the scene. Drawings appear
   * in order of function calls.
   */
  public void draw() {
    //TODO: put drawing stuff here
  }
  public static void main(String[] args) {
    String[] processingArgs = {"window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);
  }
}
