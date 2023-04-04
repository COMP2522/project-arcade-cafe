package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;
import java.awt.*;
import java.util.ArrayList;

/**
 *  Window class extending PApplet.
 *  Handles game window, background, game states, controls, and rendering.
 *
 * @author Eric Cho, Samuel Chua, Helen Liu, Mina Park, Mylo Yu
 * @version 2023-04-03
 */
public class Window extends PApplet {

  /** The width of the game window. */
  private static final int WINDOW_WIDTH = 960;

  /** The height of the game window. */
  private static final int WINDOW_HEIGHT = 540;

  /** The speed at which the background image scrolls. */
  private static final int BACKGROUND_SPEED = 2;

  /** The width of the background image. */
  private static final int BACKGROUND_WIDTH = 2000;

  /** The height of the background image. */
  private static final int BACKGROUND_HEIGHT = 1200;

  /** The initial x position of the player. */
  private static final int PLAYER_INITIAL_X = 500;

  /** The initial y position of the player. */
  private static final int PLAYER_INITIAL_Y = 490;

  /** The size of the player sprite. */
  private static final int PLAYER_SIZE = 20;

  /** The initial health points of the player. */
  private static final int PLAYER_HP = 5;

  /** The size of the player's bullets. */
  private static final int PLAYER_BULLET_SIZE = 20;

  /** The manager for the game's menus. */
  private MenuManager menuManager;

  /** The manager for the game's levels. */
  private LevelManager levelManager;

  /** The current state of the game. */
  private GameState gameState;

  /** The index of the current game state. */
  private int state = 0;

  /** The current y-coordinate of the background image. */
  private int bgY;

  /** The background image for the game. */
  PImage backgroundImage;

  /** The list of sprites in the game. */
  ArrayList<Sprite> sprites;

  /** Whether or not the left arrow key is pressed. */
  public boolean leftPressed = false;

  /** Whether or not the right arrow key is pressed. */
  public boolean rightPressed = false;

  /** Whether or not the game was paused on the previous frame. */
  public boolean wasPaused = false;

  /**
   * Sets the size of the game window.
   */
    public void settings() {
      size(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

  /**
   * Sets the current game state.
   *
   * @param gameState the new game state
   */
    public void setState(GameState gameState) {
//      this.state = newState;
      levelManager.setState(gameState); // Set the state in the LevelManager instance as well
    }

  /**
   * Initializes the game and its managers.
   */
    public void setup() {
    menuManager = new MenuManager(this, this::setState);
    backgroundImage = loadImage("src/bgImg/galagaSpace.png");
    backgroundImage.resize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);

    BulletManager.getInstance(this);
    EnemyManager.getInstance(this);
    PowerUpManager.getInstance(PLAYER_INITIAL_X , width*4/5, this);
    Player.getInstance(PLAYER_INITIAL_X, PLAYER_INITIAL_Y, PLAYER_BULLET_SIZE, this, PLAYER_HP , PLAYER_SIZE );
      levelManager = LevelManager.getInstance();

    sprites = new ArrayList<Sprite>();
    sprites.add(Player.getInstance());
  }

  /**
   * Scrolls the background image.
   */
  public void scrollingBg() {
    GameState currentState = levelManager.getState();

    if (currentState == GameState.PLAYING) {
      image(backgroundImage, 0, bgY);
      image(backgroundImage, 0, bgY - backgroundImage.height);
      backgroundImage.resize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
      bgY += BACKGROUND_SPEED;

      if (bgY >= backgroundImage.height) {
        bgY -= backgroundImage.height;
      }
    }
  }

  /**
   * Draws the game and its menus.
   */
  public void draw() {
    GameState currentState = levelManager.getState(); // Get the current state from LevelManager

    scrollingBg();

    if (currentState == GameState.PLAYING) { // Only update and draw the game elements if state is PLAYING
      update();
      levelManager.draw();
    } else {
      background(0); // Clear the screen with a black background
      image(backgroundImage, 0, 0);
    }

    menuManager.draw(currentState); // Draw the menu based on the current state
  }

  /**
   * Handles key presses for the game.
   * Pauses the game when the space bar is pressed,
   * and sets the appropriate boolean value to true
   * when the left or right arrow key is pressed.
   */
  @Override
  public void keyPressed() {
    if(key == ' ' && !wasPaused) {
      levelManager.pause();
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
   * Handles key releases for the game.
   * Resumes the game when the space bar is released,
   * and sets the appropriate boolean value to false
   * when the left or right arrow key is released.
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
   * Updates the game state.
   */
  public void update() {
    levelManager.update();
//    System.out.println(state);
  }

  /**
   * Handles mouse clicks for the game.
   * Passes the current game state to the menu manager to handle mouse clicks appropriately.
   */
  @Override
  public void mousePressed() {
    menuManager.mousePressed(levelManager.getState());
  }

  /**
   * runs the game.
   * @param args unused.
   */
  public static void main(String[] args) {

    String[] processingArgs = {"Window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);
  }
}
