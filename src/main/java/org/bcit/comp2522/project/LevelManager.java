package org.bcit.comp2522.project;

import java.util.ArrayList;
import java.util.Iterator;

import static processing.core.PApplet.dist;

public class LevelManager{
  private static LevelManager lm;
  public boolean paused = false;
  private Player player;
  private EnemyManager em;
  private BulletManager bm;
  private PowerUpManager pm;
  private LivesManager lives;
  private ScoreManager sc;
  private MenuManager Mm;
  private int state = 0;
  private int score;
  private int highscore;

  private GameState gameState;

  private LevelManager() {
    em = EnemyManager.getInstance();
    bm = BulletManager.getInstance();
    pm = PowerUpManager.getInstance();
    player = Player.getInstance();
    gameState = GameState.MAIN_MENU;
    lives = new LivesManager(player, player.window, 3); // set initial HP to 1
    score = 0;
    //TODO: read this from database
    highscore = 0;
    this.setup();
    sc = ScoreManager.getInstance(player.window);
    Mm = new MenuManager(player.window, this::setState);
  }

  public void setState(GameState newState) {
    this.gameState = newState;
  }
  public static LevelManager getInstance() {
    if(lm == null) {
      lm = new LevelManager();
    }
    return lm;
  }
  public GameState getState() {
    return gameState;
  }

  public boolean getPauseStatus() {
    return paused;
  }

  public void setup(){
    em.addEnemy();
  }

  public void pause(){
    if(paused){
      paused = false;
    } else{
      paused = true;
    }
  }
  private boolean gameOver = false;

  public boolean isGameOver() {
    return gameOver;
  }
  public void resetGameOver() {
    this.gameOver = false;
  }

  public void draw() {
    if (gameState == GameState.MAIN_MENU || gameState == GameState.GAME_OVER || gameState == GameState.SCORE_BOARD || gameState == GameState.PAUSED) {
      Mm.draw(gameState);
    } else {
      em.draw();
      bm.draw();
      pm.draw();
      player.draw();
      lives.draw();
      sc.draw();
    }
  }
//  public void draw() {
//    em.draw();
//    bm.draw();
//    pm.draw();
//    player.draw();
//    lives.draw();
//    sc.draw();
//    if (isGameOver()) {
//      Mm.draw(getState());
//    }
//  }

  public void update(){
    if(!paused) {
      em.update();
      bm.update();
      pm.update(player);
      player.update();
      checkBulletCollisions(bm, em,pm);
      pm.checkCollisions(player,lives);

      if (player.getHp() == 0) {
        setState(GameState.GAME_OVER);
        resetGame();
//        Mm.setState(); // Set the state to 3 (game over)
//        gameOver = true; // Update the gameOver flag to true
      }

      ArrayList<Enemy> copy = new ArrayList<>(em.getEnemy());
      for (Enemy enemy : copy) {
        if (collidesWithEnemy(enemy, player)) {
          // Remove the enemy from the list
          em.removeEnemy(enemy);
          // Player has been hit by an enemy, decrease HP
          // You could also add other effects, like playing a sound or showing an animation
          System.out.println("Player hit by enemy, HP decreased by 1");
        }
      }
    }
  }

  public void resetGame() {
    resetGameOver();
    resetPlayerLives();
    sc.resetScore();
    em.resetEnemy();
    // Add any other necessary resets here
  }

  public void resetPlayerLives() {
    player.setHp(3); // Set the initial HP to 3 or any other value you prefer
  }

  public void checkBulletCollisions(BulletManager bulletManager, EnemyManager enemyManager, PowerUpManager powerUpManager) {
    ArrayList<Bullet> bullets = bulletManager.getBullets();
    ArrayList<Enemy> enemies = enemyManager.getEnemy();
    ArrayList<PowerUp> powerUps = powerUpManager.getPowerUp();

    Iterator<Bullet> bulletIterator = bullets.iterator();
    while (bulletIterator.hasNext()) {
      Bullet bullet = bulletIterator.next();

      Iterator<Enemy> enemyIterator = enemies.iterator();
      while (enemyIterator.hasNext()) {
        Enemy enemy = enemyIterator.next();

        if (collidesWith(bullet, enemy)) {
          enemyIterator.remove(); // remove the enemy if it collided with a bullet
          bulletIterator.remove(); // remove the bullet if it collided with an enemy
          sc.increaseScore(1); // increase the score by 1
          break;
        }
      }
    }
  }

  public boolean collidesWith(Bullet bullet, Enemy enemy) {
    float distance = dist(bullet.getX(), bullet.getY(), enemy.getX(), enemy.getY());
    float minDistance = (bullet.getSize() + enemy.getSize()) / 2;
    return distance <= minDistance;
  }

  public boolean collidesWithEnemy(Enemy enemy, Player player) {
    float distance = dist(player.getX(), player.getY(), enemy.getX(), enemy.getY());
    float minDistance = (player.getSize() + enemy.getSize()) / 2;

    if (distance <= minDistance) {
      player.setHp(player.getHp() - 1);
      return true;
    }
    return false;
  }

}
