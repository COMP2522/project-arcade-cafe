package org.bcit.comp2522.project;

import processing.core.PApplet;
import processing.core.PImage;
import java.awt.*;
import java.util.ArrayList;

public class Window extends PApplet {

  int state = 0;
  private PauseMenu pauseMenu;
  private StartMenu startMenu;
  private LevelManager lm;
  private DatabaseHandler ds;

  private int bgY;

  PImage backgroundImage;
  ArrayList<Sprite> sprites;

  public boolean leftPressed = false;
  public boolean rightPressed = false;
  public boolean wasPaused = false;

  public void settings() {
    size(960, 540);
  }

  public void setup() {
    startMenu = new StartMenu(this, this::setState);
    backgroundImage = loadImage("src/bgImg/galagaSpace.png");

    BulletManager.getInstance(this);
    EnemyManager.getInstance(this);
    PowerUpManager.getInstance(100, width*4/5, this); // Adjust spawnTime and spawnArea as needed
    //TODO: tweak to find a good amount of HP and Firerate once we got a game going
    Player.getInstance(500, 490, 20, new Color(255, 255, 0), this,5,20);
    lm = LevelManager.getInstance();
    pauseMenu = new PauseMenu(this);

    sprites = new ArrayList<Sprite>();
    sprites.add(Player.getInstance());
  }

  public void setState(int newState) {
    state = newState;
  }

  public void scrollingBg() {
    // Move the background image down
    backgroundImage.resize(2000, 1200);
    image(backgroundImage, 0, bgY);
    image(backgroundImage, 0, bgY - backgroundImage.height);

    // Update the position of the background image
    bgY += 2;
    if (bgY >= backgroundImage.height) {
      bgY -= backgroundImage.height;
    }
  }

  public void draw() {
    switch (state) {
      // main menu
      case 0:
        scrollingBg();
        startMenu.draw();
        break;
      // start game
      case 1:
        scrollingBg();
        update();
        image(backgroundImage, 0, 0);
        backgroundImage.resize(2000, 1200);
        lm.draw();
        if(lm.paused) {
          pauseMenu.draw();
        }
        break;
      // Score Board
      case 2:
        scrollingBg();
        startMenu.drawScoreboard();
        break;
      // case N:
      // Add more states as needed
      // break;
      default:
        break;
    }
  }

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
  }
  public void mousePressed() {
    switch (state) {
      case 0:
        startMenu.mousePressed();
        break;
      case 2:
        startMenu.mousePressedScoreboard();
        break;
    }
  }

  public static void main(String[] args) {
    String[] processingArgs = {"Window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);
  }
}