package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.util.function.Consumer;

public class MenuManager {
    private PApplet pApplet;
    private Consumer<Integer> onStateChange;
    private StartMenu startMenu;
    private GameOverMenu gameOverMenu;
    private PauseMenu pauseMenu;

    private ScoreboardMenu scoreboardMenu;

    public MenuManager(PApplet pApplet, Consumer<Integer> onStateChange) {
        this.pApplet = pApplet;
        this.onStateChange = onStateChange;
        this.startMenu = new StartMenu(pApplet);
        this.gameOverMenu = new GameOverMenu(pApplet, onStateChange);
        this.pauseMenu = new PauseMenu(pApplet);
        this.scoreboardMenu = new ScoreboardMenu(pApplet);
    }

    public void draw(int state) {
        switch (state) {
            case 0:
                startMenu.draw();
                break;
            case 2:
                scoreboardMenu.draw();
                break;
            case 3:
                gameOverMenu.draw();
                break;
            case 4:
                pauseMenu.draw();
                break;
            default:
                break;
        }
    }

    public void mousePressed(int state) {
        switch (state) {
            case 0:
                startMenu.mousePressed();
                break;
            case 2:
                scoreboardMenu.mousePressed();
            case 3:
                gameOverMenu.mousePressed();
                break;
            case 4:
                pauseMenu.onMousePressed();
                break;
            default:
                break;
        }
    }
}
