package org.bcit.comp2522.project;

import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class Window extends PApplet {

  int state = 0;
  private StartMenu startMenu;
  private LevelManager lm;
  ArrayList<Sprite> sprites;

  public boolean leftPressed = false;
  public boolean rightPressed = false;
  public boolean wasPaused = false;

  public void settings() {
    size(960, 540);
  }

  public void setup() {
    startMenu = new StartMenu(this, this::setState);

    BulletManager.getInstance(this);
    EnemyManager.getInstance(this);
    PowerUpManager.getInstance(200, width*4/5, this); // Adjust spawnTime and spawnArea as needed
    //TODO: tweak to find a good amount of HP and Firerate once we got a game going
    Player.getInstance(500, 490, 20, new Color(255, 255, 0), this,5,6);
    lm = LevelManager.getInstance();

    sprites = new ArrayList<Sprite>();
    sprites.add(Player.getInstance());
  }

  public void setState(int newState) {
    state = newState;
  }

  public void draw() {
    switch (state) {
      // main menu
      case 0:
        background(0);
        startMenu.draw();
        break;
      // start game
      case 1:
        background(0); // clear the background
        update();
        lm.draw();
        break;
      // Score Board
      case 2:
        background(0);
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
    if(key == ' ') {
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