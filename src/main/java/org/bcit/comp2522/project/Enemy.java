package org.bcit.comp2522.project;

import processing.core.PImage;

/**
 The Enemy class extends the Sprite class and represents an enemy object in a game.
 It has an array of alien images that it cycles through to animate its movement.
 */
public class Enemy extends Sprite {
  private final PImage[] alienImages = new PImage[2]; // An array of alien images to be used for animation
  private final int SHIFT_DOWN = 1; // The number of pixels that the enemy should shift down by
  private final int IMAGE1 = 0;
  private final int IMAGE2 = 1;
  private final int IMAGE_SIZE = 100;
  private final int IMAGE_RATIO = -30 / 2;
  private final int TIMER_END = 10; // The number of frames that should pass before the image changes
  private int timer = 0;
  private int imageIndex = 0;

  /**
   Constructor for creating an Enemy object
   @param xPos    The x-coordinate of the enemy object
   @param yPos    The y-coordinate of the enemy object
   @param size    The size of the enemy object
   @param window  The Window object in which the enemy object is displayed
   */
  public Enemy(int xPos, int yPos, int size, Window window) {
    super(xPos, yPos, size, window);
    alienImages[IMAGE1] = window.loadImage("src/img/alienImgOne.png");
    alienImages[IMAGE1].resize(IMAGE_SIZE,IMAGE_SIZE);
    alienImages[IMAGE2] = window.loadImage("src/img/alienImgOne(Fr).png");
    alienImages[IMAGE2].resize(IMAGE_SIZE,IMAGE_SIZE);
  }

  /**
   Method to update the position of the enemy object
   */
  public void update() {
    move(0, SHIFT_DOWN);
  }


  /**
   Method to draw the enemy object on the screen
   */
  @Override
  public void draw() {
    timer++;
    if (timer >= TIMER_END) { // Change image every 10 frames
      imageIndex = (imageIndex + 1) % 2; // Cycle through the two images
      timer = 0;
    }
    window.pushMatrix();
    window.translate(getX(), getY());
    window.image(alienImages[imageIndex], IMAGE_RATIO, IMAGE_RATIO, IMAGE_SIZE, IMAGE_SIZE);
    window.popMatrix();
  }

}