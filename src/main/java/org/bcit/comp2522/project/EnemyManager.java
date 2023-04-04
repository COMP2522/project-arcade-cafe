package org.bcit.comp2522.project;

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
        Enemy enemy = new Enemy(x, y, size, window);
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
  int enemiesAtBottom = 0;
  for (Enemy enemy : enemies) {
    enemy.update();
    if (enemy.getY() + enemy.getSize() > height) {
      enemiesAtBottom++;
    }
  }

  if (enemiesAtBottom == enemies.size() && enemies.size() != 0) {
    // all enemies have reached the bottom, create a new wave of enemies
    enemies.clear();
    //When any enemies reach the bottom of the screen, player loses an HP.
    int decreaseHP = Player.getInstance().getHp() - 1;
    Player.getInstance().setHp(decreaseHP);
    SaveHandler saveHandler = new SaveHandler();
    new Thread (() -> saveHandler.saveState()).start();
    addEnemy();
  } else if (enemies.size() == 0) {
    // the wave has been defeated, create a new wave with increased difficulty
    numWaves += 1;
    yStart -= size + enemyPad;

    SaveHandler saveHandler = new SaveHandler();
    new Thread (() -> saveHandler.saveState()).start();
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

  public void resetEnemy() {
    // Reset the enemies list
    enemies.clear();

    // Reset the number of waves
    numWaves = 1;

    // Reset the starting y-coordinate
    yStart = 20;

    // Add the initial enemies back
    addEnemy();
  }

  public int getNumWaves(){
    return numWaves;
  };
  public int getYStart(){
    return yStart;
  };


}
