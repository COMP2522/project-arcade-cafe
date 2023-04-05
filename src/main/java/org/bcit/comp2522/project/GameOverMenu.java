package org.bcit.comp2522.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.json.simple.parser.ParseException;
import processing.core.PApplet;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.descending;

/**
 The GameOverMenu class provides a graphical user interface for displaying the game over screen and
 allowing the user to navigate back to the main menu.
 */
public class GameOverMenu {

    private PApplet pApplet;
    private Window window;
    private ArrayList<Button> buttons;
    private final int BUTTON_WIDTH;
    private final int BUTTON_HEIGHT;
    private final int BUTTON_SPACING;
    private final int STARTX;
    private final int STARTY;
    private final int FONT_SIZE = 20;
    private final int SHIFT_DOWN = 50;
    private final Consumer<GameState> onStateChange;
    private MenuManager menuManager;
    private LevelManager levelManager;
    private ScoreManager scoreManager;

    private DatabaseHandler db;
//    private int score;

    /**
     * Constructs a GameOverMenu object.
     *
     * @param pApplet        the PApplet object used to render the menu
     * @param onStateChange  a Consumer object that changes the GameState when the player clicks a button
     */
    public GameOverMenu(PApplet pApplet, Consumer<GameState> onStateChange) {
        int halfWidth = pApplet.width / 2;
        int halfHeight = pApplet.height / 2;
        this.pApplet = pApplet;
        this.onStateChange = onStateChange;
        this.BUTTON_WIDTH = 150;
        this.BUTTON_HEIGHT = 50;
        this.BUTTON_SPACING = 20;
//        this.levelManager = LevelManager.getInstance();
        this.scoreManager = ScoreManager.getInstance(pApplet);
//        this.menuManager = menuManager.getInstance(pApplet, onStateChange);
        STARTX = halfWidth;
        STARTY = halfHeight + SHIFT_DOWN;
        addButton("Main Menu", STARTX, STARTY, BUTTON_WIDTH, BUTTON_HEIGHT,
                    FONT_SIZE, 0xFFFFFFFF, this::goBackToMainMenu);

        ObjectMapper mapper = new ObjectMapper();
        DatabaseHandler.Config config;
        try {
            config = mapper.readValue(new File("src/main/java/org/bcit/comp2522/project/config.json"), DatabaseHandler.Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Use the values from the Config object to create the DatabaseHandler
        db = new DatabaseHandler(config.getDB_USERNAME(), config.getDB_PASSWORD());
    }


    /**
     * Draws the game over screen.
     */
    public void draw() {
        int halfWidth = pApplet.width / 2;
        int halfHeight = pApplet.height / 2;
        pApplet.background(0);
        pApplet.textSize(32);
        pApplet.fill(255);
        pApplet.textAlign(PApplet.CENTER);

        pApplet.text("GAME OVER", pApplet.width / 2, pApplet.height / 2 - 50);

        Document mostRecentScore = db.database.getCollection("score")
                .find()
                .sort(Sorts.descending("date"))
                .first();
        int score = mostRecentScore.getInteger("score");

        pApplet.text("Score: " + score, pApplet.width/2, pApplet.height/2);

        for (Button button : buttons) {
            button.draw(pApplet);
        }
    }

    /**
     * Responds to a mouse press event.
     */
    public void mousePressed() {
        for (Button button : buttons) {
            if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
                button.onClick();
                System.out.println("Go back main menu button clicked");
            }
        }
    }

    /**
     * Returns the player to the main menu.
     */
    public void goBackToMainMenu() {
        LevelManager.getInstance().resetGameOver();
        LevelManager.getInstance().setState(GameState.MAIN_MENU);
        LevelManager.getInstance().resetGame();
    }

    /**
     * Adds button to the ArrayList. If ArrayList is not initialized, initializes ArrayList.
     */
    private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight,
                           int fontSize, int fontColour, Runnable onClickAction) {
        if (buttons == null) {
            buttons = new ArrayList<>();
        }
        buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
    }
}
