package org.bcit.comp2522.project;

import java.util.ArrayList;
import java.util.Iterator;
import static processing.core.PApplet.dist;

public class LevelManager {

  private static LevelManager lm;
  private Player player;
  private EnemyManager em;
  private BulletManager bm;
  private PowerUpManager pm;
  private int score;
  private int highscore;

  private LevelManager() {
    em = EnemyManager.getInstance();
    bm = BulletManager.getInstance();
    pm = PowerUpManager.getInstance();
    player = Player.getInstance();
    score = 0;
    //TODO: read this from database
    highscore = 0;
    this.setup();
  }
  public static LevelManager getInstance() {
    if(lm == null) {
      lm = new LevelManager();
    }
    return lm;
  }

  public void setup(){
    //TODO: put initialization of arrays here.
    em.addEnemy(new ArrayList<Enemy>());
  }
  public void draw() {
    em.draw();
    bm.draw();
    pm.draw();
    player.draw();
  }
  public void update(){
    em.update();
    bm.update();
    pm.update();
    player.update();
    checkBulletCollisions(bm, em);
    pm.checkCollisions(player);
  }

  public void checkBulletCollisions(BulletManager bulletManager, EnemyManager enemyManager) {
    ArrayList<Bullet> bullets = bulletManager.getBullets();
    ArrayList<Enemy> enemies = enemyManager.getEnemies();

    Iterator<Bullet> bulletIterator = bullets.iterator();
    while (bulletIterator.hasNext()) {
      Bullet bullet = bulletIterator.next();

      Iterator<Enemy> enemyIterator = enemies.iterator();
      while (enemyIterator.hasNext()) {
        Enemy enemy = enemyIterator.next();

        if (collidesWith(bullet, enemy)) {
          enemyIterator.remove(); // remove the enemy if it collided with a bullet
          bulletIterator.remove(); // remove the bullet if it collided with an enemy
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

//  previous version - keeping for reference for now
//  public void checkPowerUpCollisions(Player player, PowerUpManager powerUpManager) {
//    ArrayList<PowerUp> powerUps = powerUpManager.getPowerUps();
//
//    Iterator<PowerUp> powerUpIterator = powerUps.iterator();
//    while (powerUpIterator.hasNext()) {
//      PowerUp powerUp = powerUpIterator.next();
//
//      if (collidesWith(player, powerUp)) {
//        powerUp.applyEffect(player); // apply the power-up effect to the player
//        powerUpIterator.remove(); // remove the power-up if it collided with the player
//      }
//    }
//  }
//
//  public boolean collidesWith(Player player, PowerUp powerUp) {
//    float distance = dist(player.getX(), player.getY(), powerUp.getX(), powerUp.getY());
//    float minDistance = (player.getSize() + powerUp.getSize()) / 2;
//    return distance <= minDistance;
//  }

}
