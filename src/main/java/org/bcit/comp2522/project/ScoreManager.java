package org.bcit.comp2522.project;

import processing.core.PApplet;

/**
 * The ScoreManager class handles the player's score during the game.
 * It provides methods to increase the score, reset it, get it, and display it on the screen.
 *
 * @author Eric Cho
 *
 */
public final class ScoreManager {
  private static ScoreManager singleton;
  private int score;
  private PApplet window;


  /**
   * Constructs a ScoreManager object.
   * Initializes the score to 0 and assigns the PApplet window.
   *
   * @param applet the PApplet window used to display the score
   */
  private ScoreManager(final PApplet applet) {
    this.score = 0;
    this.window = applet;
  }

  /**
   * Returns the instance of the ScoreManager object.
   * If the object hasn't been created yet, creates it and returns it.
   *
   * @param window the PApplet window used to display the score
   * @return the instance of the ScoreManager object
   */
  public static ScoreManager getInstance(final PApplet window) {
    if (singleton == null) {
      singleton = new ScoreManager(window);
    }
    return singleton;
  }

  /**
   * Increases the score by the given amount.
   *
   * @param amount the amount by which to increase the score
   */
  public void increaseScore(final int amount) {
    this.score += amount;
  }

  /**
   * Displays the score on the screen using the PApplet window.
   */
  public void draw() {
    window.textSize(32);
    window.fill(255);
    window.text("Score: " + score, 60, 60);
  }

  /**
   * Resets the score to 0.
   */
  public void resetScore() {
    this.score = 0;
  }

  /**
   * Returns the current score.
   *
   * @return the current score
   */
  public int getScore() {
    return this.score;
  }
}
