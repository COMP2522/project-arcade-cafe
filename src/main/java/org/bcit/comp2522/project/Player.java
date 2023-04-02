package org.bcit.comp2522.project;

import processing.core.PImage;

import java.awt.*;

public class Player extends Sprite{

  private static Player player;
  private int hp;
  private int fireRate;
  private int shotLast = 0;
  //TODO: tweak to find a good amount of speed.
  private final int speed = 10;
  private PImage playerImage;
  private int fireRateIncreases;
  private int fireRateDecreaseTimer;
  private long fireRateDecreaseStartTime;

  private Player(int x, int y, int s, Color c, Window window, int hp, int fr) {
    super(x, y, s, c, window);
    this.hp = hp;
    fireRate = fr;
    isIdle();
  }

  public void isIdle() {
    playerImage = window.loadImage("src/img/playerImgIdle.png");
    playerImage.resize(100,100);
  }

  public void isMovingRight(){
    playerImage = window.loadImage("src/img/playerImgMovingRight.png");
    playerImage.resize(100,100);
  }

  public void isMovingLeft() {
    playerImage = window.loadImage("src/img/playerImgMovingLeft.png");
    playerImage.resize(100,100);
  }

  // Checks the returned int values to call according directional animations.
  public void playerAnimation(String animCase) {
    if (animCase == "Idle") {
      isIdle();
    }else if (animCase == "MovingLeft"){
      isMovingLeft();
    } else if (animCase == "MovingRight") {
        isMovingRight();
      }
  }


  public static Player getInstance() {
    if(player == null) {
      System.out.println("ERROR: instantiate player first");
    }
    return player;
  }
  public static Player getInstance(int x, int y, int s, Color c, Window window, int hp, int fr) {
    if(player == null) {
      player = new Player(x,y,s,c,window,hp,fr);
    }
    return player;
  }

  public int getX() {return x;}
  public int getY() {return y;}
  public int getHp() {return hp;}
  public int getFireRate(){return fireRate;}
  public void setX(int x) {this.x = x;}
  public void setY(int y) {this.y = y;}
  public void setHp(int hp) {this.hp = hp;}
  public void setFireRate(int fr) {this.fireRate = fr;}
  private void shoot(int x, int y) {
    BulletManager.getInstance().shootBullet(x,y,-15);
  }
  public int getFireRateIncreases() {
    return fireRateIncreases;
  }
  public void setFireRateIncreases(int fireRateIncreases) {
    this.fireRateIncreases = fireRateIncreases;
  }

  public long getFireRateDecreaseStartTime() {
    return fireRateDecreaseStartTime;
  }

  public void setFireRateDecreaseStartTime(long fireRateDecreaseStartTime) {
    this.fireRateDecreaseStartTime = fireRateDecreaseStartTime;
  }

  public void update() {
    if(shotLast > 0) {
      shotLast--;
    }
    if (shotLast <= 0) {
      this.shoot(x, y);
      shotLast = fireRate;
    }
    if (window.leftPressed) {
      move(speed * -1, 0);
      if(x <= size) {
        x = size;
      }
      playerAnimation("MovingLeft");
//      USE THIS TO CHECK PLAYER MOVING RIGHT STATUS
//      System.out.println("Player has moved left.");
    } else if (window.rightPressed) {
      move(speed, 0);
      if(x >= window.width - size){
        x = window.width - size;
      }
      playerAnimation("MovingRight");
//      USE THIS TO CHECK PLAYER MOVING RIGHT STATUS
//      System.out.println("Player has moved right.");
    } else {
      playerAnimation("Idle");
//      USE THIS TO CHECK PLAYER IDLE STATUS
//      System.out.println("Player is idle.");
    }

  }

  @Override
  public void draw() {
    window.pushMatrix();
    window.translate(x, y);
    window.imageMode(window.CENTER);
    window.image(playerImage, 0, 0);
    window.popMatrix();
  }

}

