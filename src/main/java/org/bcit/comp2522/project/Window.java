package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Window extends PApplet {

  private MenuManager menuManager;
  private LevelManager lm;

  private GameState gameState;
  private int state = 0;
  private int bgY;

  PImage backgroundImage;
  ArrayList<Sprite> sprites;

  public boolean leftPressed = false;
  public boolean rightPressed = false;
  public boolean wasPaused = false;

    public void settings() {
      size(960, 540);
    }
    public void setState(GameState gameState) {
//      this.state = newState;
      lm.setState(gameState); // Set the state in the LevelManager instance as well
    }
    public void setup() {
    menuManager = new MenuManager(this, this::setState);
    backgroundImage = loadImage("src/bgImg/galagaSpace.png");
    backgroundImage.resize(2000, 1200);

    BulletManager.getInstance(this);
    EnemyManager.getInstance(this);
    PowerUpManager.getInstance(500, width*4/5, this);
    Player.getInstance(500, 490, 20, this, 5, 20);
    lm = LevelManager.getInstance();

    sprites = new ArrayList<Sprite>();
    sprites.add(Player.getInstance());
  }

  public void scrollingBg() {
    GameState currentState = lm.getState();

    if (currentState == GameState.PLAYING) {
      image(backgroundImage, 0, bgY);
      image(backgroundImage, 0, bgY - backgroundImage.height);
      backgroundImage.resize(2000, 1200);
      bgY += 2;

      if (bgY >= backgroundImage.height) {
        bgY -= backgroundImage.height;
      }
    }
  }

//  public void scrollingBg() {
//    image(backgroundImage, 0, bgY);
//    image(backgroundImage, 0, bgY - backgroundImage.height);
//    backgroundImage.resize(2000,1200);
//    bgY += 2;
//    if (bgY >= backgroundImage.height) {
//      bgY -= backgroundImage.height;
//    }
//  }

  public void draw() {
    GameState currentState = lm.getState(); // Get the current state from LevelManager

    scrollingBg();

    if(currentState != GameState.PLAYING) {
      background(0); // Clear the screen with a black background
      image(backgroundImage, 0, 0);
    }

    if (currentState == GameState.PLAYING) { // Only update the game elements if state is PLAYING
      update();
    }
    if (currentState == GameState.PLAYING || currentState == GameState.PAUSED) { // Only  draw the game elements if state is PLAYING or PAUSED
      lm.draw();
    }

    menuManager.draw(currentState); // Draw the menu based on the current state
  }

//  public void draw() {
//    image(backgroundImage, 0, 0);
//    scrollingBg();
//    GameState currentState = lm.getState(); // Get the current state from LevelManager
//    if (currentState == GameState.MAIN_MENU) { // Only update and draw the game elements if state is 1
//      update();
//      lm.draw();
//    }
//    menuManager.draw(currentState); // Draw the menu based on the current state
//  }

  @Override
  public void keyPressed() {
    if(key == ' ' && !wasPaused) {
      lm.pause();
      wasPaused = true;
    }
    if(key == CODED) {
      if(keyCode == LEFT) {
        leftPressed = true;
      }
      if(keyCode == RIGHT) {
        rightPressed = true;
      }
    }
  }
  @Override
  public void keyReleased() {
    if(key == ' ' && wasPaused) {
      wasPaused = false;
    }
    if(key == CODED) {
      if(keyCode == LEFT) {
        leftPressed = false;
      }
      if(keyCode == RIGHT) {
        rightPressed = false;
      }
    }
  }

  public void update() {
    lm.update();
//    System.out.println(state);
  }
  @Override
  public void mousePressed() {
    menuManager.mousePressed(lm.getState());
  }
  public static void main(String[] args) {
    String[] processingArgs = {"Window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);
  }
}
