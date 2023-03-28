package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {
  private static EnemyManager singleton;
  private ArrayList<Enemy> enemies;
  private int enemyPad = 30;
  private int waveNumber;
  private int numEnemies = 15;
  private int increment = 15;
  private int numWaves = 1;
  private Window window;
  private final Object lock = new Object();
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
        Enemy enemy = new Enemy(x, y, size, new Color(255, 0, 255), window, health);
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
    boolean annihilated = false;
    for (Enemy enemy : enemies) {
      enemy.update();
      if (enemy.getY() + enemy.getSize() > height) {
        reachedBottom = true;
      }
      if (enemies.size() == 0) {
        annihilated = true;
      }
    }
    if (reachedBottom) {
      // create a new wave of enemies
      enemies.clear();
      yStart -= size + enemyPad;
      addEnemy();
    }
    if (annihilated) {
      numEnemies += increment;
      addEnemy();
    }
  }

}
