package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {
  private static EnemyManager singleton;
  private ArrayList<Enemy> enemies;
  private int enemyPad = 30;
  private int numEnemies = 15;
  private int numWaves = 1;
  private Window window;
  private int size = 30;
  private int xStart = 60;
  private int yStart = 20;
  private int health = 210;
  private int height = 600;

  private EnemyManager(Window window) {
    this.window = window;
    this.enemies = new ArrayList<Enemy>();
  }
  public static EnemyManager getInstance(){
    return singleton;
  }
  public static EnemyManager getInstance(Window window){
    if(singleton == null) {
      singleton = new EnemyManager(window);
    }
    return singleton;
  }

  public void addEnemy() {

    for (int j = 0; j < numWaves; j++) {
      int y = yStart + (size) * j;
      for (int i = 0; i < numEnemies; i++) {
        int x = xStart + (size + enemyPad) * i;
        Enemy enemy = new Enemy(x, y, size, window, health);
        this.enemies.add(enemy);
      }
    }
  }

  public void draw() {
    for (Enemy enemy : enemies) {
      enemy.draw();
    }
  }

  public void update() {
    boolean reachedBottom = false;
    if (enemies.size() == 0) {
      numWaves += 1;
      yStart -= size + enemyPad;
      addEnemy();
    }
    for (Enemy enemy : enemies) {
      enemy.update();
      if (enemy.getY() + enemy.getSize() > height) {
        reachedBottom = true;
      }
    }

    if (reachedBottom) {
      // create a new wave of enemies
      enemies.clear();
      addEnemy();
    }
  }

  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }

  public void removeEnemy(Enemy enemy) {
    for (int i = 0; i < enemies.size(); i++) {
      if (enemies.get(i) == enemy) {
        enemies.remove(i);
        break;
      }
    }
  }
}
