package org.bcit.comp2522.project;

import processing.core.PApplet;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Window extends PApplet {

  int state = 0;
  private StartMenu startMenu;
  private BulletManager bulletManager;
  ArrayList<Sprite> sprites;

  ArrayList<Enemy> enemies;

  private EnemyManager enemyManager;

  ArrayList<PowerUp> powerUps;
  private PowerUpManager powerUpManager;

  public boolean leftPressed = false;
  public boolean rightPressed = false;

  private Timer shootBulletTimer;

  public void settings() {
    size(960, 540);
  }

  public void setup() {
    startMenu = new StartMenu(this, this::setState);
    bulletManager = new BulletManager(this);

    //TODO: tweak to find a good amount of HP and Firerate once we got a game going
    Player.getInstance(500, 500, 20, new Color(255, 255, 0), this,5,120);
    enemyManager = new EnemyManager(this);
    enemies = new ArrayList<Enemy>(); // initialize enemies list
    enemyManager.addEnemy(enemies);
    sprites = new ArrayList<Sprite>();

    powerUps = new ArrayList<PowerUp>();
//    powerUps.add(new PowerUp(200, 200, 10, new Color(255, 255, 0), this, "fireRate"));
    powerUpManager = PowerUpManager.getInstance(5, 300, this); // Adjust spawnTime and spawnArea as needed
    powerUpManager.spawn();


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

//        Player.getInstance().draw();
        for (Enemy enemy : enemies) {
          enemy.draw();
        }
//        enemyManager.draw();
        enemyManager.update();
        Player player = Player.getInstance();
        player.update(); // update player's position
        if (leftPressed) {
          player.moveLeft();
        }
        if (rightPressed) {
          player.moveRight();
        }
        player.draw();

        // drawing code for game
        if (shootBulletTimer == null) {
          // start shooting bullets
          shootBulletTimer = new Timer();
          shootBulletTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
              bulletManager.shootBullet(Player.getInstance().getX(), Player.getInstance().getY(), -2);
            }
          }, 0, 200); // Shoot a bullet every 200 milliseconds
        }
        for (PowerUp powerUp : powerUps) {
          powerUp.draw();
        }
        powerUpManager.update();
        powerUpManager.draw();

        bulletManager.drawBullets();

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
    if (key == ' ') {
      bulletManager.shootBullet(Player.getInstance().getX(), Player.getInstance().getY(), -2);
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

    if (leftPressed) {
      Player.getInstance().moveLeft();
    }
    if (rightPressed) {
      Player.getInstance().moveRight();
    }

    bulletManager.updateBullets(); // Add this line

    // Update the positions of the enemies
    //TODO: add this to enemy manager
//    enemyManager.update();
    for (Enemy enemy: enemies) {
      enemy.update();
    }
    // Update the positions of the bullets
    // Use bulletManager.getBullets() to get the list of bullets
    for (Bullet bullet : bulletManager.getBullets()) {
      bullet.update();
    }

    // Update the positions of the powerups
    for (PowerUp powerUp : powerUps) {
      powerUp.update();
    }

    powerUpManager.checkCollisions(Player.getInstance(), bulletManager);


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