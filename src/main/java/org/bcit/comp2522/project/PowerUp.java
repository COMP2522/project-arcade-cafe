package org.bcit.comp2522.project;

import processing.core.PImage;

/**
 * The PowerUp class represents a power-up object in the game.
 * A power-up can have different types, such as "hp" (health) or "fireRate" (fire rate).
 * The power-up object moves vertically and is drawn with a corresponding image.
 */
public class PowerUp extends Sprite {

  /**
   * The type of the power-up. Possible values are "hp" (health) and "fireRate" (fire rate).
   */
  private String type;

  /**
   * The count of health points for the power-up.
   */
  public int hpCount;

  /**
   * The count of fire rate points for the power-up.
   */
  public int fireCount;

  /**
   * The image of the health power-up.
   */
  private PImage hpImage;

  /**
   * The image of the fire rate power-up.
   */
  private PImage fireRateImage;

  /**
   * Constructs a new PowerUp object with the specified properties.
   *
   * @param xpos   The x position of the power-up.
   * @param ypos   The y position of the power-up.
   * @param size   The size of the power-up.
   * @param window The window in which the power-up will be rendered.
   * @param type   The type of the power-up, either "hp" or "fireRate".
*/
  public PowerUp(int xpos, int ypos, int size, Window window, String type) {
    super(xpos, ypos, size, window);
    this.type = type;
    hpImage = window.loadImage("src/img/hpPowerUp.png");
    hpImage.resize(30, 30);
    fireRateImage = window.loadImage("src/img/frPowerUp.png");
    fireRateImage.resize(30, 30);
  }

  /**
   * Returns the type of the power-up.
   *
   * @return The type of the power-up, either "hp" or "fireRate".
   */
  public String getType() {
    return type;
  }

  /**
   * Moves the power-up by the specified x and y amounts.
   *
   * @param dx The amount to move the power-up in the x direction.
   * @param dy The amount to move the power-up in the y direction.
   */
  @Override
  public void move(int dx, int dy) {
    super.move(dx, dy);
  }

  /**
   * Draws the power-up at its current position with the corresponding image
   * based on its type.
   */
  @Override
  public void draw() {
    window.pushMatrix();
    window.translate(getX(), getY());
    if (type.equals("hp")) {
      window.image(hpImage, -30 / 2, -30 / 2, 30, 30);
    } else if (type.equals("fireRate")) {
      window.image(fireRateImage, -30 / 2, -30 / 2, 30, 30);
    }
    window.popMatrix();
  }

  /**
   * Updates the power-up's position, moving it vertically.
   */
  public void update() {
    // Move the powerup vertically
    move(0, 2);
  }

}