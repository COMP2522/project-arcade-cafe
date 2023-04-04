package org.bcit.comp2522.project;

import processing.core.PImage;

public class Bullet extends Sprite {
  private int dy;
  private PImage bulletImage;


  public Bullet(int xPos, int yPos, int size, Window window, int dy) {
    super(xPos, yPos, size, window);
    this.dy = dy;
    bulletImage = window.loadImage("src/img/bullet.png");
  }

  public void update() {
    // Move the bullet vertically
    move(0, dy);
  }

  @Override
  public void draw() {
    bulletImage = window.loadImage("src/img/bullet.png");
    Window window = getWindow();
    window.imageMode(window.CENTER);
    window.image(bulletImage, x, y );
  }

  public int getSpeed(){
    return dy;
  }
}

