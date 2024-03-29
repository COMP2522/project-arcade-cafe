package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Window class is the main Processing sketch that handles the game loop and user input.
 * It also initializes and manages the different game components such as the menu, player,
 * enemies, and score.
 *
 * @author Mylo Yu
 * @author Eric Cho
 * @author Helen Liu
 * @author Sunmin Park
 * @author Samuel Chua
 *
 */
public class Window extends PApplet {
  private MenuManager menuManager;
  private LevelManager lm;
  private int bgY;
  PImage backgroundImageInGame;
  public boolean leftPressed = false;
  public boolean rightPressed = false;
  public boolean wasPaused = false;

  /**
   * Method to set the size of the game window.
   */
  public void settings() {
    size(960, 540);
  }

  /**
   * Method to set the state of the game.
   *
   * @param gameState The GameState enum value to set the state of the game to.
   */
  public void setState(GameState gameState) {
    lm.setState(gameState); // Set the state in the LevelManager instance as well
  }

  /**
   * Method to set up the game by initializing the different game components.
   */
  public void setup() {
    menuManager = MenuManager.getInstance(this, this::setState);

    backgroundImageInGame = loadImage("src/bgImg/galagaSpace.png");
    backgroundImageInGame.resize(2000, 1200);

    BulletManager.getInstance(this);
    EnemyManager.getInstance(this);
    Player.getInstance(500, 490, 20, this, 3, 20);
    ScoreManager.getInstance(this);
    PowerUpManager.getInstance(500, width * 4 / 5, this);

    lm = LevelManager.getInstance();

    autoSave();
  }

  /**
   * Method to draw the game by checking the current state of the game and updating the
   * different game components.
   */
  public void draw() {
    GameState currentState = lm.getState(); // Get the current state from LevelManager
    // Draw the menu based on the current state
    menuManager.draw(currentState);
    if (currentState == GameState.PLAYING) {
      // Draw the scrolling background
      image(backgroundImageInGame, 0, bgY);
      image(backgroundImageInGame, 0, bgY - backgroundImageInGame.height);
      bgY += 2;

      if (bgY >= backgroundImageInGame.height) {
        bgY -= backgroundImageInGame.height;
      }

      lm.draw();
      update();
    }
  }

  /**
   * Override method to check if a key has been pressed.
   */
  @Override
  public void keyPressed() {
    GameState currentState = lm.getState();
    if (currentState == GameState.PLAYING && key == ' ' && !wasPaused) {
      lm.pause();
      wasPaused = true;
    }
    if (key == CODED) {
      if (keyCode == LEFT) {
        leftPressed = true;
      }
      if (keyCode == RIGHT) {
        rightPressed = true;
      }
    }
  }

  /**
   * Method to check if a key has been released.
   */
  @Override
  public void keyReleased() {
    if (key == ' ' && wasPaused) {
      wasPaused = false;
    }
    if (key == CODED) {
      if (keyCode == LEFT) {
        leftPressed = false;
      }
      if (keyCode == RIGHT) {
        rightPressed = false;
      }
    }
  }

  /**
   * Update method that checks the status of the game to read data.
   */
  public void update() {

    lm.update();
    // Check if the game is over
    if (lm.isGameOver()) {
      // Store the score in the finalScore variable
      int finalScore = ScoreManager.getInstance(this).getScore();
      // Change the game state to GAME_OVER
      lm.setState(GameState.GAME_OVER);
      System.out.println(finalScore);
    }
  }

  /**
   * Autosave method that uses asynch to check if the game is playing.
   * If this is true, it will auto save using saveState every 30 seconds.
   */
  public void autoSave() {
    new Thread(() -> {
      while (true) {
        try {
          // Check if the game is currently playing before saving
          if (lm.getState() == GameState.PLAYING) {
            System.out.println("Game has auto-saved.");
            SaveHandler.saveState();
          }
          Thread.sleep(30000); // Wait for 30 seconds
        } catch (InterruptedException e) {
          // Thread was interrupted, stop saving
          break;
        }
      }
    }).start();
  }

  /**
   * Method to check the state of the mouse being clicked.
   */
  @Override
  public void mousePressed() {
    menuManager.mousePressed(lm.getState());
  }

  /**
   * Main method.
   *
   * @param args unused
   */
  public static void main(String[] args) {

    String[] processingArgs = {"Window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);

  }
}
