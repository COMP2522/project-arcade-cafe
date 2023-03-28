package org.bcit.comp2522.project;

import java.awt.*;
import processing.core.PImage;

public class Player extends Sprite{

  private static Player player;
  private int hp;
  private int fireRate;
  private int shotLast = 0;
  //TODO: tweak to find a good amount of speed.
  private final int speed = 5;
  private PImage playerImage;

  private Player(int x, int y, int s, Color c, Window window, int hp, int fr){
    super(x,y,s, c, window);
    this.hp = hp;
    fireRate = fr;
    playerImage = window.loadImage("src/img/playerImgIdle.png");
    playerImage.resize(100,100);
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
    //TODO: make shoot instantiate a bullet
    BulletManager.getInstance().shootBullet(x,y,-15);
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
      playerImage = window.loadImage("src/img/playerImgMovingLeft.png");
      playerImage.resize(100,100);
    } else if (window.rightPressed) {
      move(speed, 0);
      playerImage = window.loadImage("src/img/playerImgMovingRight.png");
      playerImage.resize(100,100);
    } else {
      playerImage = window.loadImage("src/img/playerImgIdle.png");
      playerImage.resize(100,100);
    }

  }
  public void moveLeft() {
    playerImage = window.loadImage("src/img/playerImgMovingLeft.png");
    playerImage.resize(100,100);
    this.x -= this.speed;
  }

  public void moveRight() {
    playerImage = window.loadImage("src/img/playerImgMovingRight.png");
    playerImage.resize(100,100);
    this.x += this.speed;
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

