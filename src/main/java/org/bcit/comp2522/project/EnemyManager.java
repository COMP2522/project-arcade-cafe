package org.bcit.comp2522.project;

import java.util.ArrayList;

/**
 * The EnemyManager class handles the management of Enemy objects, including their creation,
 * updates, and removal. It also keeps track of the state of the game, such as the number of
 * rows of enemies.
 *
 * @author Helen Liu
 * @author Mylo Yu
 * @author Eric Cho
 * @author Samuel Chua
 */
public class EnemyManager {
  private static EnemyManager singleton;
  private ArrayList<Enemy> enemies;
  private Window window;
  private int size = 30;
  private int numEnemies = 15;
  private int height = 600;
  private int enemyPadding = 30;
  private int xstart = 60;
  private int ystart = 20;
  private int numRows = 1;

  /**
   * Constructs a new EnemyManager object with the specified window.
   *
   * @param window the window object to be used for creating and rendering enemies
   */
  private EnemyManager(Window window) {
    this.window = window;
    this.enemies = new ArrayList<Enemy>();
  }

  /**
   * Returns the singleton instance of the EnemyManager object.
   *
   * @return the singleton instance of the EnemyManager object
   */
  public static EnemyManager getInstance() {
    return singleton;
  }

  /**
   * Returns the singleton instance of the EnemyManager object with the specified window. If the
   * singleton does not exist, it is created.
   *
   * @param window the window object to be used for creating and rendering enemies
   * @return the singleton instance of the EnemyManager object
   */
  public static EnemyManager getInstance(Window window) {
    if (singleton == null) {
      singleton = new EnemyManager(window);
    }
    return singleton;
  }

  /**
   * Sets the number of rows in a wave.
   *
   * @param rows The number of rows in a wave.
   */
  public void setNumRows(int rows) {
    this.numRows = rows;
  }

  /**
   * Sets the starting y-coordinate position.
   *
   * @param y The starting y-coordinate position.
   */
  public void setyStart(int y) {
    this.ystart = y;
  }

  /**
   * Returns the number of rows in enemy wave.
   *
   * @return the number of rows in enemy wave
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * Returns the starting y-coordinate position.
   *
   * @return the starting y-coordinate position
   */
  public int getyStart() {
    return ystart;
  }

  /**
   * Adds enemy to ArrayList.
   *
   * @param e The enemy object.
   */
  public void add(Enemy e) {
    enemies.add(e);
  }

  /**
   * Adds a new wave of enemies to the game with the specified number of rows and enemies per row.
   */
  public void addEnemy() {
    // for every row of enemies
    for (int j = 0; j < numRows; j++) {
      int y = ystart + (size) * j;
      // each row contains fifteen enemies
      for (int i = 0; i < numEnemies; i++) {
        int x = xstart + (size + enemyPadding) * i;
        Enemy enemy = new Enemy(x, y, size, window);
        this.enemies.add(enemy);
      }
    }
  }

  /**
   * Draws all enemies currently active in the game.
   */
  public void draw() {
    for (Enemy enemy : enemies) {
      enemy.draw();
    }
  }

  /**
   * Updates the state of all enemies in the game. If all enemies in the current wave have been
   * defeated, a new wave is created with an additional row of enemies. If any enemies in a wave
   * reach the bottom alive, the player loses an HP.
   */
  public void update() {
    int enemiesAtBottom = 0;
    // count number of enemies that reach bottom of screen
    for (Enemy enemy : enemies) {
      enemy.update();
      if (enemy.getY() + enemy.getSize() > height) {
        enemiesAtBottom++;
      }
    }

    if (enemiesAtBottom == enemies.size() && enemies.size() != 0) {
      // all enemies have reached the bottom, create a new wave of enemies
      enemies.clear();
      // when any enemies reach the bottom of the screen, player loses an HP.
      int decreaseHp = Player.getInstance().getHp() - 1;
      Player.getInstance().setHp(decreaseHp);
      addEnemy();
    } else if (enemies.size() == 0) {
      // the wave has been defeated, create a new wave with increased difficulty
      numRows += 1;
      ystart -= size + enemyPadding;

      addEnemy();
    }
  }

  /**
   * Returns an ArrayList of all the enemies currently managed by this EnemyManager.
   *
   * @return an ArrayList of all the enemies currently managed by this EnemyManager.
   */
  public ArrayList<Enemy> getEnemy() {
    return enemies;
  }

  /**
   * Removes the specified enemy from the list of enemies managed by this EnemyManager.
   *
   * @param enemy the enemy to remove from the list.
   */
  public void removeEnemy(Enemy enemy) {
    for (int i = 0; i < enemies.size(); i++) {
      if (enemies.get(i) == enemy) {
        enemies.remove(i);
        break;
      }
    }
  }

  /**
   * Resets the list of enemies managed by this EnemyManager to its initial state.
   * This function clears the list of enemies, resets the number of rows to 1,
   * sets the starting y-coordinate to 20, and adds the initial wave of enemies back.
   */
  public void resetEnemy() {
    int rows = 1;
    int ystart = 20;

    // Reset the enemies list
    enemies.clear();

    // Reset the number of waves
    setNumRows(rows);

    // Reset the starting y-coordinate
    setyStart(ystart);
  }

}
