package org.bcit.comp2522.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Sorts;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.bson.Document;
import processing.core.PApplet;


/**
 * The GameOverMenu class provides a graphical user interface
 * for displaying the game over screen and
 * allowing the user to navigate back to the main menu.
 *
 * @author Eric Cho
 * @author Helen Liu
 *
 */
public class GameOverMenu {

  private PApplet papplet;
  private Window window;
  private ArrayList<Button> buttons;
  private final int buttonWidth;
  private final int buttonHeight;
  private final int buttonSpacing;
  private final int startx;
  private final int starty;
  private final int fontSize = 20;
  private final int shiftDown = 50;
  private static final int BUTTON_WIDTH = 150;
  private static final int BUTTON_HEIGHT = 50;
  private static final int FONT_SIZE_LARGE = 32;
  private static final int FONT_SIZE_MEDIUM = 20;
  private final Consumer<GameState> onStateChange;
  private MenuManager menuManager;
  private LevelManager levelManager;
  private ScoreManager scoreManager;
  private DatabaseHandler db;
  private int halfWidth;
  private int halfHeight;

  /**
   * Constructs a GameOverMenu object.
   *
   * @param papplet        the PApplet object used to render the menu
   * @param onstatechange  a Consumer object that changes
   *                       the GameState when the player clicks a button
   */
  public GameOverMenu(final PApplet papplet,
                      final Consumer<GameState> onstatechange) {
    halfWidth = papplet.width / 2;
    halfHeight = papplet.height / 2;
    this.papplet = papplet;
    this.onStateChange = onstatechange;
    this.buttonWidth = BUTTON_WIDTH;
    this.buttonHeight = BUTTON_HEIGHT;
    this.buttonSpacing = FONT_SIZE_MEDIUM;
    this.scoreManager = ScoreManager.getInstance(papplet);

    startx = halfWidth;
    starty = halfHeight + shiftDown;
    addButton("Main Menu", startx, starty, buttonWidth, buttonHeight,
            fontSize, 0xFFFFFFFF, this::goBackToMainMenu);

    ObjectMapper mapper = new ObjectMapper();
    DatabaseHandler.Config config;

    try {
      config = mapper.readValue(new File("src/main/java/org/bcit/comp2522/project/config.json"),
                DatabaseHandler.Config.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // Use the values from the Config object to create the DatabaseHandler
    db = new DatabaseHandler(config.getDbUsername(),
          config.getDbPassword());
  }


  /**
   * Draws the game over screen.
   */
  public void draw() {
    int halfWidth = papplet.width / 2;
    int halfHeight = papplet.height / 2;
    papplet.background(0);
    papplet.textSize(FONT_SIZE_LARGE);
    papplet.fill(255);
    papplet.textAlign(PApplet.CENTER);

    papplet.text("GAME OVER", papplet.width / 2, papplet.height / 2 - 50);

    Document mostRecentScore = db.database.getCollection("score")
            .find()
            .sort(Sorts.descending("date"))
            .first();
    int score = mostRecentScore.getInteger("score");

    papplet.text("Score: " + score, papplet.width / 2, papplet.height / 2);

    for (Button button : buttons) {
      button.draw(papplet);
    }
  }

  /**
   * Responds to a mouse press event.
   */
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(papplet.mouseX, papplet.mouseY)) {
        button.onClick();
        System.out.println("Go back main menu button clicked");
      }
    }
  }

  /**
   * Returns the player to the main menu.
   */
  public void goBackToMainMenu() {
    LevelManager.getInstance().resetGameOverStatus();
    LevelManager.getInstance().setState(GameState.MAIN_MENU);
    LevelManager.getInstance().resetGame();
  }

  /**
   * Adds button to the ArrayList.
   * If ArrayList is not initialized, initializes ArrayList.
   */
  private void addButton(final String label,
                         final float x,
                         final float y,
                         final float buttonwidth,
                         final float buttonheight,
                         final int fontsize,
                         final int fontcolour,
                         final Runnable onClickAction) {
    if (buttons == null) {
      buttons = new ArrayList<>();
    }
    buttons.add(new Button(label,
            x, y, buttonwidth, buttonheight,
            fontsize, fontcolour, onClickAction));
  }
}
