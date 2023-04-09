package org.bcit.comp2522.project;

import processing.core.PImage;

/**
 * The Bullet class extends the Sprite class and represents a bullet object in a game.
 * It moves vertically and is drawn on the screen as an image.
 */

public class Bullet extends Sprite {
  private int dy; //The speed of the bullet
  private PImage bulletImage; // Image that is used to draw the bullet.


  /**
   * Constructor for creating a Bullet object.
   *
   * @param xpos The x-coordinate of the bullet object
   * @param ypos The y-coordinate of the bullet object
   * @param size The size of the bullet object
   * @param window The Window object in which the bullet object is displayed
   * @param dy The speed at which the bullet moves vertically
   */
  public Bullet(int xpos, int ypos, int size, Window window, int dy) {
    super(xpos, ypos, size, window);
    this.dy = dy;
    bulletImage = window.loadImage("src/img/bullet.png");
  }

  /**
   * Method to update the position of the bullet object.
   */
  public void update() {
    // Move the bullet vertically
    move(0, dy);
  }

  /**
   * Method to draw the bullet object on the screen.
   */
  @Override
  public void draw() {
    bulletImage = window.loadImage("src/img/bullet.png");
    Window window = getWindow();
    window.imageMode(window.CENTER);
    window.image(bulletImage, xpos, ypos);
  }

  /**
   * Method to get the speed of the bullet.
   *
   * @return The speed of the bullet
   */
  public int getSpeed() {
    return dy;
  }
}

