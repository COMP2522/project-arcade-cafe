package org.bcit.comp2522.project;

import java.util.function.Consumer;
import processing.core.PApplet;

/**
 * The MenuManager class is responsible for managing the different menus and
 * screens that are displayed throughout the game. It keeps track of the current
 * game state and calls the appropriate methods of each menu when necessary.
 */
public class MenuManager {
  private PApplet papplet;
  private Consumer<GameState> onStateChange;
  private StartMenu startMenu;
  private GameOverMenu gameOverMenu;
  private PauseMenu pauseMenu;

  private GameState gameState;
  private static MenuManager singleton;
  private ScoreboardMenu scoreboardMenu;
  private int score;

  /**
   * Constructs a new MenuManager object with the specified PApplet and
   * Consumer objects. Initializes the different menus and the game state.
   *
   * @param papplet the PApplet object used to render the menus
   * @param onStateChange a Consumer object that changes the gamestate on player click.
   */
  public MenuManager(PApplet papplet, Consumer<GameState> onStateChange) {
    this.papplet = papplet;
    this.onStateChange = onStateChange;
    this.startMenu = new StartMenu(papplet, onStateChange);
    this.gameOverMenu = new GameOverMenu(papplet, onStateChange);
    this.pauseMenu = new PauseMenu(papplet);
    this.scoreboardMenu = new ScoreboardMenu(papplet);
  }

  /**
   * Returns the singleton instance of the MenuManager object.
   * If the object has not been instantiated, creates a new instance
   * and returns it.
   *
   * @param papplet the PApplet object used to render the menus
   * @param onStateChange a Consumer object that changes
   *                      the GameState when the player clicks a button
   * @return the singleton instance of the MenuManager object
   */
  public static MenuManager getInstance(PApplet papplet, Consumer<GameState> onStateChange) {
    if (singleton == null) {
      singleton = new MenuManager(papplet, onStateChange);
    }
    return singleton;
  }

  /**
   * Draws the current menu based on the current game state.
   * Calls the draw method of the appropriate menu.
   *
   * @param gameState the current game state
   */
  public void draw(GameState gameState) {
    switch (gameState) {
      case MAIN_MENU:
        startMenu.draw();
        break;
      case SCORE_BOARD:
        scoreboardMenu.draw();
        break;
      case GAME_OVER:
        gameOverMenu.draw();
        break;
      case PAUSED:
        pauseMenu.draw();
        break;
      default:
        break;
    }
  }

  /**
   * Responds to a mouse press event.
   * Calls the mousePressed method of the appropriate menu.
   *
   * @param gameState the current game state
   */
  public void mousePressed(GameState gameState) {
    switch (gameState) {
      case MAIN_MENU:
        startMenu.mousePressed();
        break;
      case SCORE_BOARD:
        scoreboardMenu.mousePressed();
        break;
      case GAME_OVER:
        gameOverMenu.mousePressed();
        break;
      case PAUSED:
        pauseMenu.onMousePressed();
        break;
      default:
        break;
    }
  }
}
