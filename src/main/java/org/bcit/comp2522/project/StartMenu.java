package org.bcit.comp2522.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import processing.core.PImage;

import javax.xml.crypto.Data;

public class StartMenu extends PApplet{

  private PApplet pApplet;
  private ArrayList<Button> buttons;
  private boolean buttonsInitialized = false;
  private Consumer<Integer> onStateChange;

  private Button goBackButton;

  DatabaseHandler db;


  // MENU SETUP //

  public StartMenu(PApplet pApplet, Consumer<Integer> onStateChange) {
    this.pApplet = pApplet;
    buttons = new ArrayList<>();
    this.onStateChange = onStateChange;
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

  public void draw() {
    if (!buttonsInitialized) {
      addButton("Start", pApplet.width / 2, pApplet.height / 2 - 50, 100, 50, 20, 0xFFFFFFFF, this::startGame);
      addButton("Scoreboard", pApplet.width / 2, pApplet.height / 2 + 50, 100, 50, 20, 0xFFFFFFFF, this::openScoreboard);
      addButton("Exit", pApplet.width / 2, pApplet.height / 2 + 150, 100, 50, 20, 0xFFFFFFFF, this::exitGame);
      buttonsInitialized = true;
    }

    pApplet.background(0);
    for (Button button : buttons) {
      button.draw(pApplet);
    }
  }

  // BUTTON INTERACTION FUNCTIONS //
  public void mousePressed() {
    for (Button button : buttons) {
      if (button.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
        button.onClick();
      }
    }
  }

  private void addButton(String label, float x, float y, float buttonWidth, float buttonHeight, int fontSize, int fontColour, Runnable onClickAction) {
    buttons.add(new Button(label, x, y, buttonWidth, buttonHeight, fontSize, fontColour, onClickAction));
  }

  public void startGame() {
    //TODO: Implement this method to start the game
    if (onStateChange != null) {
      onStateChange.accept(1); // Set the state to 1
    }
  }

  public void openScoreboard() {
    if (onStateChange != null) {
      onStateChange.accept(2); // Set the state to 2 for the Scoreboard
    }
  }
  public void gameOver() {
    if (onStateChange != null) {
      onStateChange.accept(3); // Set the state to 3 (game over)
    }
  }

  public void exitGame() {
    pApplet.exit();
  }

  public void drawScoreboard() {
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
//      pApplet.text("High Scores", pApplet.width , pApplet.height);
      Document highestScore = topScores.get(0);
      int value = highestScore.getInteger("score");
      pApplet.text(String.format("Highest Score: %d", value), pApplet.width / 2, y);
      y += 50;
    } else {
      System.out.println("empty");
    }

    // Draw the 2nd to 10th highest scores
    for (int i = 1; i < Math.min(topScores.size(), 10); i++) {
      Document score = topScores.get(i);
      int value = score.getInteger("score");
      pApplet.text(String.format("%d. %d", i + 1, value), pApplet.width / 2, y);
      y += 50;
    }

    // Draw the go back button
    if (goBackButton == null) {
      goBackButton = new Button("Go Back", pApplet.width / 2, pApplet.height - 50, 100, 50, 20, 0xFFFFFFFF, this::goBackToMainMenu);
    }
    goBackButton.draw(pApplet);
  }

  public void drawGameOver() {
    // Create a new window to display the game over message
//    pApplet.background(0);
    PApplet gameoverWindow = new PApplet();
    gameoverWindow.size(400, 200);
    gameoverWindow.background(255, 0, 0);
    gameoverWindow.textSize(32);
    gameoverWindow.fill(255);
    gameoverWindow.textAlign(PApplet.CENTER);
    gameoverWindow.text("GAME OVER", gameoverWindow.width / 2, gameoverWindow.height / 2);
  }

  public void goBackToMainMenu() {
    if (onStateChange != null) {
      onStateChange.accept(0); // Set the state to 0 (main menu)
    }
  }

  public void mousePressedScoreboard() {
    if (goBackButton != null && goBackButton.isMouseOver(pApplet.mouseX, pApplet.mouseY)) {
      goBackButton.onClick();
    }
  }
}
