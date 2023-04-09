package org.bcit.comp2522.project;

/**
 * The following enum classes define the different states that the window is in.
 * The states are:
 * MAIN_MENU: the main menu screen
 * PLAYING: the game is currently being played
 * PAUSED: the game is paused
 * GAME_OVER: the game has ended and the game over screen is displayed
 * SCORE_BOARD: the scoreboard screen is displayed
 */
public enum GameState {
    MAIN_MENU,
    PLAYING,
    PAUSED,
    GAME_OVER,
    SCORE_BOARD
}
