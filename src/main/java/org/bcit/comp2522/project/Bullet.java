package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

public class Bullet extends Sprite {
  private int dy;


  public Bullet(int xPos, int yPos, int size, Color color, Window window, int dy) {
    super(xPos, yPos, size, color, window);
    this.dy = dy;
  }

  public void update() {
    // Move the bullet vertically
    move(0, dy);
  }

  @Override
  public void draw() {
    PApplet pApplet = getPApplet();
    pApplet.fill(color.getRGB());
    pApplet.ellipse(x, y, size, size);
  }
}

