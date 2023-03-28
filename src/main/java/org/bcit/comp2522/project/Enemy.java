package org.bcit.comp2522.project;

import processing.core.PImage;

import java.awt.*;

public class Enemy extends Sprite {
  private int health;
  private final int shift = 1;
  private final PImage alien1;
  private final int height = window.height;

  public Enemy(int xPos, int yPos, int size, Color color, Window window, int health) {
    super(xPos, yPos, size, color, window);
    this.health = health;
    alien1 = window.loadImage("src/img/alien2.png");
    alien1.resize(30,30);
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

  @Override
  public void draw() {
    window.pushMatrix();
    window.translate(getX(), getY());
    window.image(alien1, -30 / 2, -30 / 2, 30, 30);
    window.popMatrix();
  }

//  public void takeDamage(int damage) {
//    health -= damage;
//    if (health <= 0) {
//      // Remove the enemy from the list of enemies
//      getWindow().enemies.remove(this);
//    }
//  }

}
