package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;



public class Window extends PApplet {

  private MenuManager menuManager;
  private LevelManager lm;
  private int bgY;

  PImage backgroundImage;

  public boolean leftPressed = false;
  public boolean rightPressed = false;
  public boolean wasPaused = false;

    public void settings() {
      size(960, 540);

    }
    public void setState(GameState gameState) {
//      this.state = newState;
      lm.setState(gameState); // Set the state in the LevelManager instance as well
    }
    public void setup() {
      menuManager = MenuManager.getInstance(this, this::setState);

    backgroundImage = loadImage("src/bgImg/galagaSpace.png");
    backgroundImage.resize(2000, 1200);

    BulletManager.getInstance(this);
    EnemyManager.getInstance(this);
    Player.getInstance(500, 490, 20, this, 3, 20);
    ScoreManager.getInstance(this);
    PowerUpManager.getInstance(500, width*4/5, this);

    lm = LevelManager.getInstance();
  }

  public void scrollingBg() {
    GameState currentState = lm.getState();

    if (currentState == GameState.PLAYING) {
      image(backgroundImage, 0, bgY);
      image(backgroundImage, 0, bgY - backgroundImage.height);
      backgroundImage.resize(2000, 1200);
      bgY += 2;

      if (bgY >= backgroundImage.height) {
        bgY -= backgroundImage.height;
      }
    }
  }

  public void draw() {
    GameState currentState = lm.getState(); // Get the current state from LevelManager

    scrollingBg();

    if(currentState != GameState.PLAYING) {
      background(0); // Clear the screen with a black background
      image(backgroundImage, 0, 0);
    }

    if (currentState == GameState.PLAYING) { // Only update the game elements if state is PLAYING
      update();
    }
    if (currentState == GameState.PLAYING || currentState == GameState.PAUSED) { // Only  draw the game elements if state is PLAYING or PAUSED
      lm.draw();
    }

    menuManager.draw(currentState); // Draw the menu based on the current state
  }

  /**
   * Override method to check if a key has been pressed.
   */
  @Override
  public void keyPressed() {
    if(key == ' ' && !wasPaused) {
      lm.pause();
      wasPaused = true;
    }
    if(key == CODED) {
      if(keyCode == LEFT) {
        leftPressed = true;
      }
      if(keyCode == RIGHT) {
        rightPressed = true;
      }
    }
  }


  /**
   * Method to check if a key has been released.
   */
  @Override
  public void keyReleased() {
    if(key == ' ' && wasPaused) {
      wasPaused = false;
    }
    if(key == CODED) {
      if(keyCode == LEFT) {
        leftPressed = false;
      }
      if(keyCode == RIGHT) {
        rightPressed = false;
      }
    }
  }

  /**
   * Update method that checks the status of the game to read data.
   */
  public void update() {

    lm.update();
    if (lm.isGameOver()) { // Check if the game is over
      int finalScore = ScoreManager.getInstance(this).getScore(); // Store the score in the finalScore variable
      lm.setState(GameState.GAME_OVER); // Change the game state to GAME_OVER
      System.out.println(finalScore);
    }
  }

  /**
   * Method to check the state of the mouse being clicked.
   */
  @Override
  public void mousePressed() {
    menuManager.mousePressed(lm.getState());
  }

  /**
   * Main method
   * @param args
   */
  public static void main(String[] args) {

    String[] processingArgs = {"Window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);

  }
}
