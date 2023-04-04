package org.bcit.comp2522.project;

import processing.core.PImage;

public class Enemy extends Sprite {
  private int health;
  private final int shift = 1;
  private final PImage[] alienImages = new PImage[2];

  private int imageIndex = 0;

  private int timer = 0;
  private final int height = window.height;


  public Enemy(int xPos, int yPos, int size, Window window, int health) {
    super(xPos, yPos, size, window);
    this.health = health;
    alienImages[0] = window.loadImage("src/img/alienImgOne.png");
    alienImages[0].resize(100,100);
    alienImages[1] = window.loadImage("src/img/alienImgOne(Fr).png");
    alienImages[1].resize(100,100);
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
    timer++;
    if (timer >= 10) { // Change image every 10 frames
      imageIndex = (imageIndex + 1) % 2; // Cycle through the two images
      timer = 0;
    }
    window.pushMatrix();
    window.translate(getX(), getY());
    window.image(alienImages[imageIndex], -30 / 2, -30 / 2, 100, 100);
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