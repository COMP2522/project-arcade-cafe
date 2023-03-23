package org.bcit.comp2522.project;

public class LevelManager {

  private static LevelManager lm;
  private Player player;
  private int score;
  private int highscore;

  private LevelManager() {
    player = Player.getInstance();
    score = 0;
    //TODO: read this from database
    highscore = 0;
  }
  public LevelManager getInstance() {
    if(lm == null) {
      lm = new LevelManager();
    }
    return lm;
  }

  public void setup(){
    //TODO: put initialization of arrays here.
  }
  public void draw() {
    //TODO: put all draws here
    player.draw();
  }
  public void update(){
    //TODO: put updates here
    player.update();
  }


}
