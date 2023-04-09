package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.util.function.Consumer;

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

  public MenuManager(PApplet papplet, Consumer<GameState> onStateChange) {
    this.papplet = papplet;
    this.onStateChange = onStateChange;
    this.startMenu = new StartMenu(papplet, onStateChange);
    this.gameOverMenu = new GameOverMenu(papplet, onStateChange);
    this.pauseMenu = new PauseMenu(papplet);
    this.scoreboardMenu = new ScoreboardMenu(papplet);
  }

  public static MenuManager getInstance(PApplet papplet, Consumer<GameState> onStateChange) {
    if (singleton == null) {
      singleton = new MenuManager(papplet, onStateChange);
    }
    return singleton;
  }

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
