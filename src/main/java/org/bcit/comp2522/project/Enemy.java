package org.bcit.comp2522.project;

import processing.core.PImage;

/**
 The Enemy class extends the Sprite class and represents an enemy object in a game.
 It has an array of alien images that it cycles through to animate its movement.
 */
public class Enemy extends Sprite {
  // An array of alien images to be used for animation
  private final PImage[] alienImages = new PImage[2];
  // The number of pixels that the enemy should shift down by
  private int shiftDown = 1;
  private int image1 = 0;
  private int image2 = 1;
  private int imageSize = 100;
  private int imageRatio = -30 / 2;
  // The number of frames that should pass before the image changes
  private int timerEnd = 10;
  private int timer = 0;
  private int imageIndex = 0;

  /**
   * Constructor for creating an Enemy object.
   *
   * @param xpos     The x-coordinate of the enemy object
   * @param ypos     The y-coordinate of the enemy object
   * @param size     The size of the enemy object
   * @param window   The Window object in which the enemy object is displayed
   */
  public Enemy(int xpos, int ypos, int size, Window window) {
    super(xpos, ypos, size, window);
    alienImages[image1] = window.loadImage("src/img/alienImgOne.png");
    alienImages[image1].resize(imageSize, imageSize);
    alienImages[image2] = window.loadImage("src/img/alienImgOne(Fr).png");
    alienImages[image2].resize(imageSize, imageSize);
  }

  /**
   * Method to update the position of the enemy object.
   */
  public void update() {
    move(0, shiftDown);
  }

  /**
   * Method to draw the enemy object on the screen.
   */
  public void enemyIdleAnim(){
    timer++;
    if (timer >= timerEnd) { // Change image every 10 frames
      imageIndex = (imageIndex + 1) % 2; // Cycle through the two images
      timer = 0;
    }
  }

  /**
   Method to draw the enemy object on the screen
   */
  @Override
  public void draw() {
    enemyIdleAnim();
    window.pushMatrix();
    window.translate(getX(), getY());
    window.image(alienImages[imageIndex], imageRatio, imageRatio, imageSize, imageSize);
    window.popMatrix();
  }
}