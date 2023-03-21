package org.bcit.comp2522.project;

import processing.core.PApplet;


import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Window extends PApplet {

  private StartMenu startMenu;
  ArrayList<Sprite> sprites;
  private ArrayList<Enemy> enemies;
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
          this, 2));

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
//    for (Bullet bullet : bullets) {
//      enemy.update();
//    }

    // Check for collisions between player and enemies
//    for (Enemy enemy : enemies) {
//      if (Sprite.collided(player, enemy)) {
//      }
//    }

    // Check for collisions between player and bullets
//    for (Bullet bullet : bullets) {
//      if (Sprite.collided(player, bullet)) {
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
