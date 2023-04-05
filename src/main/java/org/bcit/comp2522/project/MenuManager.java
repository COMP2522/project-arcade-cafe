package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.util.function.Consumer;

public class MenuManager {
    private PApplet pApplet;
    private Consumer<GameState> onStateChange;
    private StartMenu startMenu;
    private GameOverMenu gameOverMenu;
    private PauseMenu pauseMenu;

    private GameState gameState;
    private static MenuManager singleton;
    private ScoreboardMenu scoreboardMenu;
    private LevelManager levelManager;
    private int score;

    public MenuManager(PApplet pApplet, Consumer<GameState> onStateChange) {
        this.pApplet = pApplet;
        this.onStateChange = onStateChange;
        this.startMenu = new StartMenu(pApplet, onStateChange);
        this.gameOverMenu = new GameOverMenu(pApplet, onStateChange);
        this.pauseMenu = new PauseMenu(pApplet);
        this.scoreboardMenu = new ScoreboardMenu(pApplet);
    }
    public static MenuManager getInstance(PApplet pApplet, Consumer<GameState> onStateChange) {
        if (singleton == null) {
            singleton = new MenuManager(pApplet, onStateChange);
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
