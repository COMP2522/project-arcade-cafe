package org.bcit.comp2522.project;

import processing.core.PApplet;


import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Window extends PApplet {

  private StartMenu startMenu;
  ArrayList<Sprite> sprites;
  ArrayList<Enemy> enemies;
  ArrayList<Bullet> bullets;
  ArrayList<PowerUp> powerUps;
  // private Bullet[] bullets;
  public boolean leftPressed = false;
  public boolean rightPressed = false;

  public void settings() {
    size(960, 540);
  }

  public void setup() {
    startMenu = new StartMenu(this);
    //TODO: tweak to find a good amount of HP and Firerate once we got a game going
    Player.getInstance(500, 500, 20, new Color(255, 255, 0), this,5,120);
    enemies = new ArrayList<Enemy>();
    sprites = new ArrayList<Sprite>();
    enemies.add(new Enemy(200, 200,
            20, new Color(255, 255, 0),
          this, 2, 10));
    bullets = new ArrayList<Bullet>();
    bullets.add(new Bullet(200, 200, 20, new Color(255, 255, 0), this, 2));

    powerUps = new ArrayList<PowerUp>();
    powerUps.add(new PowerUp(200, 200, 20, new Color(255, 255, 0), this, 2));

    sprites = new ArrayList<Sprite>();
    sprites.addAll(enemies);
    sprites.add(Player.getInstance());

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        update();
      }
    }, 0, 16);
  }

  public void draw() {
    startMenu.draw();
    Player.getInstance().draw();
    for (Enemy enemy : enemies) {
      enemy.draw();
    }
    for (Bullet bullet : bullets) {
      bullet.draw();
    }
    for (PowerUp powerUp : powerUps) {
      powerUp.draw();
    }
  }

@Override
  public void keyPressed() {
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
    Player.getInstance().update();

    // Update the positions of the enemies
    //TODO: add this to enemy manager
    for (Enemy enemy : enemies) {
      enemy.update();
    }

    // Update the positions of the bullets
    for (Bullet bullet : bullets) {
      bullet.update();
    }

    // Update the positions of the powerups
    for (PowerUp powerUp : powerUps) {
      powerUp.update();
    }

    //TODO: whoever approved the latest pull request, this code does not work with the threads
//    // Check for collisions between player and enemies
//    for (Enemy enemy : enemies) {
//      if (Sprite.collided(Player.getInstance(), enemy)) {
//      }
//    }
//
//    // Check for collisions between player and bullets
//    for (Bullet bullet : bullets) {
//      if (Sprite.collided(Player.getInstance(), bullet)) {
//      }
//    }
//
//    // Check for collisions between enemies and bullets
//    for (Bullet bullet : bullets) {
//      for (Enemy enemy : enemies) {
//        if (Sprite.collided(enemy, bullet)) {
//          enemy.takeDamage(5); // Reduce enemy's health by 1 if there is a collision
//        }
//      }
//    }
//
//    // Check for collisions between player and powerups
//    for (PowerUp powerUp : powerUps) {
//      if (Sprite.collided(Player.getInstance(), powerUp)) {
//        // upgrade player/equipment
//      }
//    }
  }
  public void mousePressed() {
    startMenu.mousePressed();
  }

  public static void main(String[] args) {
    String[] processingArgs = {"Window"};
    Window window = new Window();
    PApplet.runSketch(processingArgs, window);
  }

  public void startGame() {
    //TODO: Implement this method to start the game
  }

  public void openOptions() {
    //TODO: Implement this method to open the Options menu
  }

  public void exitGame() {
    exit();
  }
}
