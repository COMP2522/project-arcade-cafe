package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {
  private ArrayList<Enemy> enemies;
  private Window window;

  private int shift = 1;

  public EnemyManager() {
    enemies = new ArrayList<Enemy>();
  }

  public void addEnemy(Enemy enemy) {
    enemies.add(enemy);
  }

  public void draw() {
    for (Enemy enemy : enemies) {
      enemy.draw();
    }
  }

  public void update() {
    for (Enemy enemy : enemies) {
      enemy.move(shift, 0);

      // Check if enemy is off the screen
      if (enemy.getX() < 0 || enemy.getX() > window.width) {
        // Reverse direction of enemy
        shift *= -1;
      }
    }
  }

}
