package org.bcit.comp2522.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreboardMenu {

    private PApplet pApplet;
    DatabaseHandler db;
    private Button goBackButton;

    private ArrayList<Button> buttons;
    private GameState gameState;

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
        goBackButton = new Button("Go Back", pApplet.width / 2, pApplet.height - 50, 100, 50, 20, 0xFFFFFFFF, this::goBackToMainMenu);
        buttons.add(goBackButton);
    }

    public void draw() {
        ArrayList<Document> topScores = db.getTopScores();

        // Clear the screen
        pApplet.background(0);

        // Draw the title
        pApplet.textSize(64);
        pApplet.fill(255);
        pApplet.textAlign(PApplet.CENTER);
        pApplet.text("High Scores", pApplet.width / 2, pApplet.height / 8);

        // Draw the highest score
        pApplet.textSize(32);
        pApplet.fill(255);
        pApplet.textAlign(PApplet.CENTER);
        int y = pApplet.height / 4;
        if (!topScores.isEmpty()) {
            Document highestScore = topScores.get(0);
            int value = highestScore.getInteger("score");
            pApplet.text(String.format("Highest Score: %d", value), pApplet.width / 2, y);
            y += 50;
        } else {
            System.out.println("empty");
        }

        // Draw the 1st to 5th highest scores
        for (int i = 0; i < Math.min(topScores.size(), 5); i++) {
            Document score = topScores.get(i);
            int value = score.getInteger("score");
            pApplet.text(String.format("%d. %d", i + 1, value), pApplet.width / 2, y);
            y += 50;
        }

        goBackButton.draw(pApplet);
    }

    public void mousePressed() {
        for (Button button : buttons) {
            if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
                button.onClick();
                System.out.println("go back buttion clicked ");
            }
        }
    }
    public void goBackToMainMenu () {
        LevelManager.getInstance().setState(gameState.MAIN_MENU); // Set the state to 0 (main menu)
    }

}
