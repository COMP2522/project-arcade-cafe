package org.bcit.comp2522.project;

/**
 * The abstract class Sprite represents a basic game sprite with an x-coordinate,
 * y-coordinate, size, and associated window. It provides methods for getting and
 * setting the sprite's coordinates and size, moving the sprite, checking for
 * collisions with other sprites, and obtaining the sprite's associated window.
 * Subclasses must provide their own implementation of the draw method to display
 * the sprite in the window.
 *
 * @author Helen Liu
 *
 */
public abstract class Sprite {

  /**
   * The x-coordinate of the sprite.
   */
  protected int xpos;

  /**
   * The y-coordinate of the sprite.
   */
  protected int ypos;

  /**
   * The size of the sprite.
   */
  protected int size;

  /**
   * The window associated with the sprite.
   */
  protected Window window;

  /**
   * Constructs a sprite with the specified coordinates, size, and window.
   *
   * @param xpos    the x-coordinate of the sprite
   * @param ypos    the y-coordinate of the sprite
   * @param size    the size of the sprite
   * @param window  the window associated with the sprite
   */
  public Sprite(int xpos, int ypos, int size, Window window) {
    this.xpos = xpos;
    this.ypos = ypos;
    this.size = size;
    this.window = window;
  }

  /**
   * Returns the x-coordinate of the sprite.
   *
   * @return the x-coordinate of the sprite
   */
  public int getX() {
    return this.xpos;
  }

  /**
   * Sets the x-coordinate of the sprite to the specified value.
   *
   * @param xpos the new x-coordinate of the sprite
   */
  public void setX(int xpos) {
    this.xpos = xpos;
  }

  /**
   * Returns the y-coordinate of the sprite.
   *
   * @return the y-coordinate of the sprite
   */
  public int getY() {
    return this.ypos;
  }

  /**
   * Sets the y-coordinate of the sprite to the specified value.
   *
   * @param ypos the new y-coordinate of the sprite
   */
  public void setY(int ypos) {
    this.ypos = ypos;
  }

  /**
   * Returns the size of the sprite.
   *
   * @return the size of the sprite
   */
  public int getSize() {
    return size;
  }

  /**
   * Draws the sprite in the associated window. Subclasses must provide their own
   * implementation of this method.
   */
  public abstract void draw();

  /**
   * Moves the sprite by the specified amount in the x and y directions.
   *
   * @param dx the amount to move the sprite in the x direction
   * @param dy the amount to move the sprite in the y direction
   */
  public void move(int dx, int dy) {
    this.xpos += dx;
    this.ypos += dy;
  }

  /**
   * Returns true if the specified sprites collide, false otherwise.
   *
   * @param a the first sprite
   * @param b the second sprite
   * @return true if the sprites collide, false otherwise
   */
  public static boolean collided(Sprite a, Sprite b) {
    float distance = (float) Math.sqrt((b.getX() - a.getX()) * (b.getX() - a.getX())
                            + (b.getY() - a.getY()) * (b.getY() - a.getY()));
    if (distance <= (a.getSize() + b.getSize())) {
      return true;
    }
    return false;
  }

  /**
   * Returns the window associated with this Sprite.
   *
   * @return the window associated with this Sprite.
   */
  public Window getWindow() {
    return window;
  }

}