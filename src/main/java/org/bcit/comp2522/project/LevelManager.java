package org.bcit.comp2522.project;

import java.util.ArrayList;
import java.util.Iterator;

import static processing.core.PApplet.dist;

/**
 * LevelManager manages the EnemyManager, BulletManager, PowerUpManager,
 * including updating and drawing game objects, handling collisions, and managing game states.
 *
 * @author Eric Cho, Samuel Chua, Helen Liu, Mina Park, Mylo Yu
 * @version 2023-04-03
 */
public class LevelManager{

  /**
   * The initial player HP.
   */
  private static final int INITIAL_HP = 3;

  /**
   * Singleton instance of LevelManager.
   */
  private static LevelManager levelManager;

  /**
   * Boolean indicating if the game is currently paused.
   */
  public boolean paused = false;
  private Player player;
  private EnemyManager enemyManager;
  private BulletManager bulletManager;
  private PowerUpManager powerUpManager;
  private LivesManager lives;
  private ScoreManager scoreManager;
  private MenuManager menuManager;
  private int state = 0;
  private int score;
  private int highscore;

  private GameState gameState;

  /**
   * Constructs a new LevelManager instance, initializing all required fields.
   */
  private LevelManager() {
    enemyManager = EnemyManager.getInstance();
    bulletManager = BulletManager.getInstance();
    powerUpManager = PowerUpManager.getInstance();
    player = Player.getInstance();
    gameState = GameState.MAIN_MENU;
    lives = new LivesManager(player, player.window, INITIAL_HP); // set initial HP to 1
    score = 0;
    highscore = 0;
    this.setup();
    scoreManager = ScoreManager.getInstance(player.window);
    menuManager = new MenuManager(player.window, this::setState);
  }

  /**
   * Sets the current game state.
   *
   * @param newState The new game state.
   */
  public void setState(GameState newState) {
    this.gameState = newState;
  }

  /**
   * Returns the singleton instance of LevelManager.
   *
   * @return The LevelManager instance.
   */
  public static LevelManager getInstance() {
    if(levelManager == null) {
      levelManager = new LevelManager();
    }
    return levelManager;
  }

  /**
   * Returns the current game state.
   *
   * @return The current game state.
   */
  public GameState getState() {
    return gameState;
  }

  /**
   * Adds the enemies for set up.
   */
  public void setup(){
    enemyManager.addEnemy();
  }

  /**
   * For pausing the game.
   */
  public void pause(){
    if(paused){
      paused = false;
    } else{
      paused = true;
    }
  }

  /**
   * boolean to set gameOver to false.
   */
  private boolean gameOver = false;

  /**
   * Resets the gameOver flag to false.
   */
  public void resetGameOver() {
    this.gameOver = false;
  }

  /**
   * Draws game objects and menus depending on the current game state.
   */
  public void draw() {
    if (gameState == GameState.MAIN_MENU ||
        gameState == GameState.GAME_OVER ||
        gameState == GameState.SCORE_BOARD ||
        gameState == GameState.PAUSED) {
      menuManager.draw(gameState);
    } else {
      enemyManager.draw();
      bulletManager.draw();
      powerUpManager.draw();
      player.draw();
      lives.draw();
      scoreManager.draw();
    }
  }

  /**
   * Updates the game objects and checks for collisions between sprites when the game is not paused.
   */
  public void update(){
    if(!paused) {
      enemyManager.update();
      bulletManager.update();
      powerUpManager.update(player);
      player.update();
      checkBulletCollisions(bulletManager, enemyManager, powerUpManager);
      powerUpManager.checkCollisions(player,lives);

      if (player.getHp() == 0) {
        setState(GameState.GAME_OVER);
        resetGame();
//        Mm.setState(); // Set the state to 3 (game over)
//        gameOver = true; // Update the gameOver flag to true
      }

      ArrayList<Enemy> copy = new ArrayList<>(enemyManager.getEnemies());
      for (Enemy enemy : copy) {
        if (playerCollidesWithEnemy(enemy, player)) {
          // Remove the enemy from the list
          enemyManager.removeEnemy(enemy);
          // Player has been hit by an enemy, decrease HP
          // You could also add other effects, like playing a sound or showing an animation
          System.out.println("Player hit by enemy, HP decreased by 1");
        }
      }
    }
  }

  /**
   * Resets the game by resetting the gameOver flag, player lives, score, and enemies.
   */
  public void resetGame() {
    resetGameOver();
    resetPlayerLives();
    scoreManager.resetScore();
    enemyManager.resetEnemy();
    // Add any other necessary resets here
  }

  /**
   * Resets the player lives to the initial value.
   */
  public void resetPlayerLives() {
    player.setHp(INITIAL_HP); // Set the initial HP to 3
  }

  /**
   * Checks for collisions between bullets, enemies, and power-ups,
   * handling the necessary updates to the game objects and score.
   *
   * @param bulletManager The BulletManager instance.
   * @param enemyManager The EnemyManager instance.
   * @param powerUpManager The PowerUpManager instance.
   */
  public void checkBulletCollisions(BulletManager bulletManager,
                                    EnemyManager enemyManager,
                                    PowerUpManager powerUpManager) {
    ArrayList<Bullet> bullets = bulletManager.getBullets();
    ArrayList<Enemy> enemies = enemyManager.getEnemies();
    ArrayList<PowerUp> powerUps = powerUpManager.getPowerUp();

    Iterator<Bullet> bulletIterator = bullets.iterator();
    while (bulletIterator.hasNext()) {
      Bullet bullet = bulletIterator.next();

      Iterator<Enemy> enemyIterator = enemies.iterator();
      while (enemyIterator.hasNext()) {
        Enemy enemy = enemyIterator.next();

        if (bulletCollidesWithEnemy(bullet, enemy)) {
          enemyIterator.remove(); // remove the enemy if it collided with a bullet
          bulletIterator.remove(); // remove the bullet if it collided with an enemy
          scoreManager.increaseScore(1); // increase the score by 1
          break;
        }
      }
    }
  }

  /**
   * Checks for collisions between a bullet and an enemy.
   *
   * @param bullet The bullet to check for collisions.
   * @param enemy The enemy to check for collisions.
   * @return True if the bullet and enemy collide, false otherwise.
   */
  public boolean bulletCollidesWithEnemy(Bullet bullet, Enemy enemy) {
    float distance = dist(bullet.getX(), bullet.getY(), enemy.getX(), enemy.getY());
    float minDistance = (bullet.getSize() + enemy.getSize()) / 2;
    return distance <= minDistance;
  }

  /**
   * Checks for collisions between an enemy and the player.
   *
   * @param enemy The enemy to check for collisions.
   * @param player The player to check for collisions.
   * @return True if the enemy and player collide, false otherwise.
   */
  public boolean playerCollidesWithEnemy(Enemy enemy, Player player) {
    float distance = dist(player.getX(), player.getY(), enemy.getX(), enemy.getY());
    float minDistance = (player.getSize() + enemy.getSize()) / 2;

    if (distance <= minDistance) {
      player.setHp(player.getHp() - 1);
      return true;
    }
    return false;
  }

}
