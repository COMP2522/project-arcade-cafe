package org.bcit.comp2522.project;

import java.awt.*;

public class Enemy extends Sprite {
  // vertical speed of enemy
  private int dy;

  public Enemy(int xPos, int yPos, int size, Color color, Window window, int dy) {
    super(xPos, yPos, size, color, window);
    this.dy = dy;
  }

  public void update() {
    // Move the enemy vertically
    move(0, dy);

    // Check if the enemy has gone off the screen and wrap it around if it has
    if (getX() < -getSize()) {
      setX(getWindow().width);
    }
    if (getX() > getWindow().width) {
      setX(-getSize());
    }
  }

}
