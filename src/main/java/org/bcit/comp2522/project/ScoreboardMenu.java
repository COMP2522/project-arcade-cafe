package org.bcit.comp2522.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 The ScoreboardMenu class is responsible for displaying the highest scores achieved in the game.
 It uses a DatabaseHandler object to retrieve the scores from a MongoDB database.
 The class also provides a "Go Back" button to return to the main menu.
 */
public class ScoreboardMenu {
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 50;
    private final int FONT_SIZE = 20;
    private final int SHIFT = 50;
    private final int HALF = 2;
    private final int FOURTH = 4;
    private final int EIGHTH = 8;
    private PApplet pApplet;
    DatabaseHandler db;
    private Button goBackButton;
    private ArrayList<Button> buttons;
    private GameState gameState;

    /**
     Creates a new ScoreboardMenu object with the specified PApplet object.
     Initializes the buttons list, reads the database configuration from a JSON file,
     creates a DatabaseHandler object and initializes the "Go Back" button.
     @param pApplet the Processing PApplet object to be used for rendering
     */
    public ScoreboardMenu(PApplet pApplet) {
        this.pApplet = pApplet;

        this.buttons = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        DatabaseHandler.Config config;
        try {
            config = mapper.readValue(new File("src/main/java/org/bcit/comp2522/project/config.json"), DatabaseHandler.Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Use the values from the Config object to create the DatabaseHandler
        db = new DatabaseHandler(config.getDB_USERNAME(), config.getDB_PASSWORD());
        goBackButton = new Button("Go Back", pApplet.width / HALF, pApplet.height - SHIFT,
            BUTTON_WIDTH, BUTTON_HEIGHT, FONT_SIZE, 0xFFFFFFFF, this::goBackToMainMenu);
        buttons.add(goBackButton);
    }

    /**
     Renders the scoreboard menu, displaying the highest scores achieved in the game.
     The method first retrieves the top scores from the database using the DatabaseHandler object.
     The method then clears the screen and renders the title and scores using the Processing PApplet object.
     The "Go Back" button is also rendered.
     */
    public void draw() {
        ArrayList<Document> topScores = db.getTopScores();

        // Clear the screen
        pApplet.background(0);

        // Draw the title
        pApplet.textSize(64);
        pApplet.fill(255);
        pApplet.textAlign(PApplet.CENTER);
        pApplet.text("High Scores", pApplet.width / HALF, pApplet.height / EIGHTH);

        // Draw the highest score
        pApplet.textSize(32);
        pApplet.fill(255);
        pApplet.textAlign(PApplet.CENTER);
        int y = pApplet.height / FOURTH;
        if (!topScores.isEmpty()) {
            Document highestScore = topScores.get(0);
            int value = highestScore.getInteger("score");
            pApplet.text(String.format("Highest Score: %d", value), pApplet.width / HALF, y);
            y += SHIFT;
        } else {
            System.out.println("Empty");
        }

        // Draw the 1st to 5th highest scores
        for (int i = 0; i < Math.min(topScores.size(), 5); i++) {
            Document score = topScores.get(i);
            int value = score.getInteger("score");
            pApplet.text(String.format("%d. %d", i + 1, value), pApplet.width / HALF, y);
            y += SHIFT;
        }

        goBackButton.draw(pApplet);
    }

    /**
     Handles the mouse press event by iterating over all buttons and invoking the onClick method
     of the button that is currently being pressed.
     */
    public void mousePressed() {
        for (Button button : buttons) {
            if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
                button.onClick();
                System.out.println("Go back button clicked ");
            }
        }
    }

    /**
     Changes the game state to the main menu state when the user clicks on the "Go Back" button.
     */
    public void goBackToMainMenu () {
        LevelManager.getInstance().setState(gameState.MAIN_MENU); // Set the state to 0 (main menu)
    }

}
