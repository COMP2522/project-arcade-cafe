package org.bcit.comp2522.project;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * A StartMenu class for displaying the game's starting menu.
 * Extends PApplet to use Processing's graphical capabilities.
 *
 * @author Helen Liu
 * @author Eric Cho
 * @author Mylo Yu
 * @author Samuel Chua
 *
 */
public class StartMenu {
  private final PApplet papplet;
  private final PFont pixelFont;
  private final int buttonWidth = 150;
  private final int buttonHeight = 50;
  private final int fontSize = 25;
  private final int offset0 = 25;
  private final int offset1 = 75;
  private final int offset2 = 125;
  private final int offset3 = 200;
  private ArrayList<Button> buttons;
  private boolean buttonsInitialized = false;
  private final Consumer<GameState> onStateChange;
  private PImage backgroundImage;
  private boolean musicPlaying = false;


  /**
   * Constructor for the StartMenu class.
   *
   * @param papplet The PApplet instance to use processing's graphical capabilities
   * @param onstatechange A Consumer to change the game state when buttons are clicked.
   */
  public StartMenu(final PApplet papplet,
                   final Consumer<GameState> onstatechange) {
    this.papplet = papplet;
    this.onStateChange = onstatechange;
    buttons = new ArrayList<>();
    backgroundImage = papplet.loadImage("src/bgImg/gameTitle.png");
    pixelFont = papplet.createFont("src/font/pixelFont.ttf", fontSize);
  }

  /**
   * This method allows audio to play upon menu click interaction.
   *
   * @param soundFilePath specifies what is indicated through the filepath to play audio.
   */
  public static Clip playSound(String soundFilePath) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                                            new File(soundFilePath).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
      return clip;
    } catch (Exception ex) {
      System.out.println("Error playing sound.");
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * Draw the StartMenu to the screen.
   * Initializes the buttons and loads the background image.
   */
  public void draw() {
    papplet.imageMode(papplet.CORNER);
    int halfWidth = papplet.width / 2;
    int halfHeight = papplet.height / 2;
    papplet.background(0);
    papplet.image(backgroundImage, 0, 0);
    buttons.clear();
    if (musicPlaying != true) {
      playSound("src/sfx/8bit_surrender.wav");
      musicPlaying = true;
    }
    int extraOffset = 0;
    if (LevelManager.getInstance().saveExists()) {
      extraOffset = 30;
      addButton("Continue", halfWidth, halfHeight + offset0
                      + extraOffset, buttonWidth, buttonHeight, fontSize,
              0xFFFFFFFF, this::continueGame);
    }
    addButton("New Game", halfWidth, halfHeight + offset1 + extraOffset, buttonWidth,
            buttonHeight, fontSize, 0xFFFFFFFF, this::startNewGame);
    addButton("Scoreboard", halfWidth, halfHeight + offset2 + extraOffset, buttonWidth,
            buttonHeight, fontSize, 0xFFFFFFFF, this::openScoreboard);
    addButton("Exit", halfWidth, halfHeight + offset3 + extraOffset, buttonWidth,
            buttonHeight, fontSize, 0xFFFFFFFF, this::exitGame);

    // Draw text at the bottom right corner of the window
    papplet.textSize(20);
    papplet.fill(255);
    papplet.textAlign(PApplet.RIGHT, PApplet.BOTTOM);
    papplet.text("Move: Left/Right Arrow Keys", papplet.width - 20, papplet.height - 20);
    papplet.text("Pause: Space", papplet.width - 20, papplet.height - 40);

    // Set up the font
    papplet.textFont(pixelFont);
    papplet.textAlign(PApplet.CENTER, PApplet.CENTER);
    papplet.fill(0xFFFFFFFF);

    for (Button button : buttons) {
      button.draw(papplet);
    }
  }

  /**
   * Handles mouse button press events on the StartMenu.
   * Calls corresponding onClickAction methods for each button if clicked.
   */
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(papplet.mouseX, papplet.mouseY)) {
        playSound("src/sfx/ui_select.wav");
        String buttonLabel = button.getLabel();
        if (buttonLabel.equals("Start")) {
          onStateChange.accept(GameState.PLAYING);
        } else if (buttonLabel.equals("Scoreboard")) {
          onStateChange.accept(GameState.SCORE_BOARD);
        } else if (buttonLabel.equals("Exit")) {
          System.exit(0);
        }
        button.onClick();
        System.out.println("Start menu button clicked");
      }
    }
  }

  /**
   * Adds a new button to the Start Menu with the given label,
   * position, size, font size, font color, and on-click action.
   *
   * @param buttonLabel         the text displayed on the button
   * @param xbutton             the x-coordinate of the center of the button
   * @param ybutton             the y-coordinate of the center of the button
   * @param addButtonWidth      the width of the button in pixels
   * @param addButtonHeight     the height of the button in pixels
   * @param addButtonFontSize   the size of the font used for the button label
   * @param fontColour          the color of font used for button label in hexadecimal notation
   * @param onClickAction       the action to perform when the button is clicked
   */
  private void addButton(final String buttonLabel,
                         final float xbutton,
                         final float ybutton,
                         final float addButtonWidth,
                         final float addButtonHeight,
                         final int addButtonFontSize,
                         final int fontColour,
                         final Runnable onClickAction) {
    buttons.add(new Button(buttonLabel, xbutton, ybutton, addButtonWidth,
            addButtonHeight, addButtonFontSize, fontColour, onClickAction));
  }

  /**
   Changes the game state to PLAYING and parses
   save file when the Continue button is clicked.
   */
  public void continueGame() {
    SaveHandler saveHandler = new SaveHandler();
    saveHandler.parseSave();
  }
  /**
   Changes the game state to PLAYING and clears
   save files when the New Game button is clicked.
   */

  public void startNewGame() {
    File file = new File("save.json");
    if (file.exists()) {
      file.delete();
      LevelManager.getInstance().setSaveExists(false);
    }
    EnemyManager.getInstance().addEnemy();
    onStateChange.accept(GameState.PLAYING);
  }

  /**
   Changes the game state to SCORE_BOARD
   when the Scoreboard button is clicked.
   */
  public void openScoreboard() {
    onStateChange.accept(GameState.SCORE_BOARD);
  }

  /**
   Exits the game when the Exit button is clicked.
   */
  public void exitGame() {
    papplet.exit();
  }
}

