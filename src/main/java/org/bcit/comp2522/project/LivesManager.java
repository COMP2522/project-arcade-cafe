package org.bcit.comp2522.project;

import static processing.core.PApplet.dist;

import java.util.List;
import processing.core.PImage;

/**
 * Defines a LivesManager that handles the player's lives in the game.
 * Lives are represented by hearts displayed on the screen using the draw() method.
 *
 */
public class LivesManager {

  private Player player;
  private Window window;

  /**
   * The heart image used to represent a life.
   */
  private PImage heartImage;

  /**
   * The size of the heart image.
   */
  private int heartSize;

  /**
   * The padding between heart images.
   */
  private int heartPadding;

  /**
   * The padding from the left side of the window.
   */
  private int leftPadding;

  /**
   * The sum of heartPadding and leftPadding.
   */
  private int paddingSum;

  /**
   * The maximum number of HP allowed.
   */
  private static final int MAX_HP = 5;

  /**
   * Constructs a LivesManager object with the specified player, window, and initialHP.
   *
   * @param player The player object.
   * @param window The window object.
   * @param initialHp The initial number of lives for the player.
   */
  public LivesManager(Player player, Window window, int initialHp) {
    this.player = player;
    this.window = window;
    this.heartSize = 30;
    this.heartPadding = 5;
    this.leftPadding = 20;
    this.paddingSum = heartPadding + leftPadding;
    heartImage = window.loadImage("src/img/heartLife.png");
    heartImage.resize(heartSize, heartSize);
    player.setHp(initialHp);
  }

  /**
   * Draws the lives (hearts) on the window.
   */
  public void draw() {
    for (int i = 0; i < player.getHp(); i++) {
      int xpos = paddingSum + (i * (heartSize + heartPadding));
      int ypos = paddingSum;
      window.image(heartImage, xpos, ypos);
    }
  }

  /**
   * Decreases the player's lives by 1.
   */
  public void loseLife() {
    if (player.getHp() > 0) {
      player.setHp(player.getHp() - 1);
    }
  }

  /**
   * Increases the player's lives by 1, up to the maximum HP allowed at a given time.
   */
  public void gainLife() {
    if (player.getHp() < MAX_HP) {
      player.setHp(player.getHp() + 1);
    }
  }

  /**
   * Resets the player's lives to 1.
   */
  public void resetLives() {
    player.setHp(3); // Set the initial HP to 3 or any other value you prefer
  }

  /**
   * Returns the number of lives the player currently has.
   *
   * @return The number of lives the player has.
   */
  public int getLives() {
    return player.getHp();
  }


}
