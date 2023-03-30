package org.bcit.comp2522.project;

import java.util.ArrayList;
import java.util.Iterator;
import static processing.core.PApplet.dist;

public class LevelManager {

  private static LevelManager lm;
  private boolean paused = false;
  private Player player;
  private EnemyManager em;
  private BulletManager bm;
  private PowerUpManager pm;
  private LivesManager lives;
  private ScoreManager sc;
  private int score;
  private int highscore;

  private LevelManager() {
    em = EnemyManager.getInstance();
    bm = BulletManager.getInstance();
    pm = PowerUpManager.getInstance();
    player = Player.getInstance();
    lives = new LivesManager(3, player.window);
    score = 0;
    //TODO: read this from database
    highscore = 0;
    this.setup();
    sc = ScoreManager.getInstance(player.window);
  }
  public static LevelManager getInstance() {
    if(lm == null) {
      lm = new LevelManager();
    }
    return lm;
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
  public void draw() {
    em.draw();
    bm.draw();
    pm.draw();
    player.draw();
    lives.draw();
    sc.draw();
  }
  public void update(){
    if(!paused) {
      em.update();
      bm.update();
      pm.update(player);
      player.update();
      checkBulletCollisions(bm, em,pm);
      pm.checkCollisions(player,lives);
    }
  }



  public void checkBulletCollisions(BulletManager bulletManager, EnemyManager enemyManager, PowerUpManager powerUpManager) {
    ArrayList<Bullet> bullets = bulletManager.getBullets();
    ArrayList<Enemy> enemies = enemyManager.getEnemies();
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

}
