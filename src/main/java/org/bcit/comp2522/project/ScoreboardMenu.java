package org.bcit.comp2522.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bson.Document;
import processing.core.PApplet;


/**
 * The ScoreboardMenu class is responsible for displaying
 * the highest scores achieved in the game.
 * It uses a DatabaseHandler object to retrieve
 * the scores from a MongoDB database.
 * The class also provides a "Go Back" button
 * to return to the main menu.
 *
 * @author Eric Cho
 * @author Helen Liu
 * @author Samuel Chua
 *
 */
public class ScoreboardMenu {
  private final int buttonWidth = 150;
  private final int buttonHeight = 50;
  private final int fontSize = 20;
  private final int shift = 50;
  private final int half = 2;
  private final int fourth = 4;
  private final int eighth = 8;
  private PApplet papplet;
  private DatabaseHandler db;
  private Button goBackButton;
  private ArrayList<Button> buttons;
  private ArrayList<Document> topScores;
  private GameState gameState;


  /**
   * Creates a new ScoreboardMenu object with the specified PApplet object.
   * Initializes the buttons list, reads the database
   * configuration from a JSON file, creates a DatabaseHandler object
   * and initializes the "Go Back" button.
   *
   * @param papplet the Processing PApplet object to be used for rendering
   */
  public ScoreboardMenu(PApplet papplet) {
    this.papplet = papplet;

    this.buttons = new ArrayList<>();
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
    goBackButton = new Button(
            "Go Back",
            this.papplet.width / half,
            this.papplet.height - shift,
            buttonWidth,
            buttonHeight,
            fontSize,
            0xFFFFFFFF,
            this::goBackToMainMenu);
    buttons.add(goBackButton);
  }

  /**
   * Renders the scoreboard menu, displaying the highest scores
   * achieved in the game. The method first retrieves the top scores
   * from the database using the DatabaseHandler object. The method then clears
   * the screen and renders the title and scores using
   * the Processing PApplet object. The "Go Back" button is also rendered.
   */
  public void draw() {
    topScores = db.getTopScores();

    // Clear the screen
    papplet.background(0);

    // Draw the title
    papplet.textSize(64);
    papplet.fill(255);
    papplet.textAlign(PApplet.CENTER);
    papplet.text("High Scores",
            papplet.width / half,
            papplet.height / eighth);

    // Draw the highest score
    papplet.textSize(32);
    papplet.fill(255);
    papplet.textAlign(PApplet.CENTER);
    int y = papplet.height / fourth;
    if (!topScores.isEmpty()) {
      Document highestScore = topScores.get(0);
      int value = highestScore.getInteger("score");
      papplet.text(String.format("Highest Score: %d", value),
              papplet.width / half, y);
      y += shift;
    } else {
      System.out.println("Empty");
    }

    // Draw the 1st to 5th highest scores
    for (int i = 0; i < Math.min(topScores.size(), 5); i++) {
      Document score = topScores.get(i);
      int value = score.getInteger("score");
      papplet.text(String.format("%d. %d", i + 1, value),
              papplet.width / half, y);
      y += shift;
    }

    goBackButton.draw(papplet);
  }

  /**
   * Handles the mouse press event by iterating over
   * all buttons and invoking the onClick method
   * of the button that is currently being pressed.
   */
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(papplet.mouseX, papplet.mouseY)) {
        button.onClick();
        System.out.println("Go back button clicked ");
      }
    }
  }

  /**
   * Changes the game state to the main menu state
   * when the user clicks on the "Go Back" button.
   */
  public void goBackToMainMenu() {
    LevelManager.getInstance().setState(gameState.MAIN_MENU);
  }

}
