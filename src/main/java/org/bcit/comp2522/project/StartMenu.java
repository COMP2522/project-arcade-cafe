package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 A StartMenu class for displaying the game's starting menu.
 Extends PApplet to use Processing's graphical capabilities.
 */
public class StartMenu extends PApplet{
  private PApplet pApplet;
  private final int BUTTON_WIDTH = 150;
  private final int BUTTON_HEIGHT = 50;
  private final int FONT_SIZE = 20;

  private final int OFFSET0 = -50;
  private final int OFFSET1 = 25;
  private final int OFFSET2 = 100;
  private final int OFFSET3 = 175;
  private ArrayList<Button> buttons;
  private boolean buttonsInitialized = false;
  private Consumer<GameState> onStateChange;

  private Button goBackButton;

  private GameState gameState;
  PImage backgroundImage;

  DatabaseHandler db;


  /**
   Constructor for the StartMenu class.
   @param pApplet         The PApplet instance to use Processing's graphical capabilities.
   @param onStateChange   A Consumer to change the game state when buttons are clicked.
   */
  public StartMenu(PApplet pApplet,Consumer<GameState> onStateChange) {
    this.pApplet = pApplet;
    this.onStateChange = onStateChange;
    buttons = new ArrayList<>();
    backgroundImage = pApplet.loadImage("src/bgImg/gameTitle.png"); // Load the background image
  }

  /**
   Draw the StartMenu to the screen.
   Initializes the buttons and loads the background image.
   */
  public void draw() {
    int HALF_WIDTH = pApplet.width / 2;
    int HALF_HEIGHT = pApplet.height / 2;
    if (!buttonsInitialized) {
      File file = new File("save.json");
      if(file.exists()){
        addButton("Continue", HALF_WIDTH, HALF_HEIGHT + OFFSET0, BUTTON_WIDTH,
                BUTTON_HEIGHT, FONT_SIZE, 0xFFFFFFFF, this::continueGame);
      }
      addButton("New Game", HALF_WIDTH, HALF_HEIGHT + OFFSET1, BUTTON_WIDTH,
          BUTTON_HEIGHT, FONT_SIZE, 0xFFFFFFFF, this::startNewGame);
      addButton("Scoreboard", HALF_WIDTH, HALF_HEIGHT + OFFSET2, BUTTON_WIDTH,
          BUTTON_HEIGHT, FONT_SIZE, 0xFFFFFFFF, this::openScoreboard);
      addButton("Exit", HALF_WIDTH, HALF_HEIGHT + OFFSET3, BUTTON_WIDTH,
          BUTTON_HEIGHT, FONT_SIZE, 0xFFFFFFFF, this::exitGame);
      buttonsInitialized = true;
    }

    pApplet.image(backgroundImage, 0, 0, pApplet.width, pApplet.height); // Use the loaded backgroundImage

    for (Button button : buttons) {
      button.draw(pApplet);
    }
  }


  /**
   Handles mouse button press events on the StartMenu.
   Calls corresponding onClickAction methods for each button if clicked.
   */
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
        String buttonLabel = button.getLabel();
        if (buttonLabel.equals("Start")) {
          onStateChange.accept(GameState.PLAYING); // Update the gameState to PLAYING
        } else if (buttonLabel.equals("Scoreboard")) {
          onStateChange.accept(GameState.SCORE_BOARD);
        } else if (buttonLabel.equals("Exit")) {
          System.exit(0);
        }
        button.onClick();
        System.out.println("start menu button clicked");
      }
    }
  }

  /**
   Adds a new button to the Start Menu with the given label, position, size, font size, font color, and on-click action.
   @param label           the text displayed on the button
   @param x               the x-coordinate of the center of the button
   @param y               the y-coordinate of the center of the button
   @param buttonWidth     the width of the button in pixels
   @param buttonHeight    the height of the button in pixels
   @param fontSize        the size of the font used for the button label
   @param fontColour      the color of the font used for the button label in hexadecimal notation
   @param onClickAction   the action to perform when the button is clicked
   */
  private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour, Runnable onClickAction) {
    buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
  }

  /**
   Changes the game state to PLAYING and parses save file when the Continue button is clicked.
   */
  public void continueGame() {
    SaveHandler saveHandler = new SaveHandler();
    saveHandler.parseSave();
  }
  /**
   Changes the game state to PLAYING and clears save files when the New Game button is clicked.
   */
  public void startNewGame() {
    File file = new File("save.json");
    if(file.exists()){
      file.delete();
    }
    EnemyManager.getInstance().addEnemy();
    onStateChange.accept(GameState.PLAYING);
  }

  /**
   Changes the game state to SCORE_BOARD when the Scoreboard button is clicked.
   */
  public void openScoreboard() {
    onStateChange.accept(GameState.SCORE_BOARD);
  }

  /**
   Exits the game when the Exit button is clicked.
   */
  public void exitGame() {
    pApplet.exit();
  }
}

