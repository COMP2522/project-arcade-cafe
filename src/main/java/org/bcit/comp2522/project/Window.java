package org.bcit.comp2522.project;

import processing.core.PApplet;


import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Window extends PApplet {

  private StartMenu startMenu;
  private ArrayList<Sprite> sprites;
  private Player player;
  private ArrayList<Enemy> enemies;
  private ArrayList<Bullet> bullets;
  private ArrayList<PowerUp> powerUps;

  public void settings() {
    size(960, 540);
  }

  public void setup() {
    startMenu = new StartMenu(this);

    player = new Player(100, 100, 20, color(255, 255, 0), this);
    enemies = new ArrayList<Enemy>();
    enemies.add(new Enemy(200, 200,
            20, new Color(255, 255, 0),
          this, 2));
    bullets = new ArrayList<Bullet>();
    bullets.add(new Bullet(200, 200, 20, new Color(255, 255, 0), this, 2));

    powerUps = new ArrayList<PowerUp>();
    powerUps.add(new PowerUp(200, 200, 20, new Color(255, 255, 0), this, 2));

    sprites = new ArrayList<Sprite>();
    sprites.addAll(enemies);
    sprites.add(player);

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        update();
      }
    }, 0, 16);
  }

  public void draw() {
    startMenu.draw();
    player.draw();
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

  public void update() {
    player.update();

    // Update the positions of the enemies
    for (Enemy enemy : enemies) {
      enemy.update();
    }

    // Update the positions of the bullets
    for (Bullet bullet : bullets) {
      bullet.update();
    }

    // Check for collisions between player and enemies
    for (Enemy enemy : enemies) {
      if (Sprite.collided(player, enemy)) {
      }
    }

    // Check for collisions between player and bullets
    for (Bullet bullet : bullets) {
      if (Sprite.collided(player, bullet)) {
      }
    }
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
