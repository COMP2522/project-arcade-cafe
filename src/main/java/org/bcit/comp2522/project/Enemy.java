package org.bcit.comp2522.project;

import java.awt.*;

public class Enemy extends Sprite {
  // vertical speed of enemy
  private int dy;
  private int health;

  public Enemy(int xPos, int yPos, int size, Color color, Window window, int dy, int health) {
    super(xPos, yPos, size, color, window);
    this.dy = dy;
    this.health = health;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void takeDamage(int damage) {
    health -= damage;
    if (health <= 0) {
      // Remove the enemy from the list of enemies
      getWindow().enemies.remove(this);
    }
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
