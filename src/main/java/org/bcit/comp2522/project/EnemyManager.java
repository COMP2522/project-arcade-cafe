package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class EnemyManager {
  private ArrayList<Enemy> enemies;
  private int enemyPad = 30;
  private int numEnemies = 15;
  private Window window;
  private int width;
  private int height;
  private final Object lock = new Object();
  private int shift = 1;
  private int size = 30;
  private int xStart = 60;
  private int yStart = 20;
  private int health = 210;

  public EnemyManager(Window window) {
    this.window = window;
    enemies = new ArrayList<Enemy>();
    width = window.width;
    height = window.height;
  }

  public void addEnemy(ArrayList<Enemy> enemies) {
    for (int i = 0; i < numEnemies; i++) {
      int x = xStart + (size + enemyPad) * i;
      int y = yStart;
      Enemy enemy = new Enemy(x, y, size, new Color(255, 0, 255), window, health);
      enemies.add(enemy);
    }
  }

  public void draw() {
    for (Enemy enemy : enemies) {
      enemy.draw();
    }
  }

  public void update() {
    for (Enemy enemy : enemies) {
      enemy.update();
    }
  }

}
