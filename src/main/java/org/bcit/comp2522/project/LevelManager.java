package org.bcit.comp2522.project;

import java.util.ArrayList;

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
  }


}
