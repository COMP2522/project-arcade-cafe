package org.bcit.comp2522.project;

import java.awt.*;

public class Enemy extends Sprite {
  private int health;
  private int shift = 1;

  public Enemy(int xPos, int yPos, int size, Color color, Window window, int health) {
    super(xPos, yPos, size, color, window);
    this.health = health;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void update() {
    move(0, shift);
  }

//  public void takeDamage(int damage) {
//    health -= damage;
//    if (health <= 0) {
//      // Remove the enemy from the list of enemies
//      getWindow().enemies.remove(this);
//    }
//  }

}
