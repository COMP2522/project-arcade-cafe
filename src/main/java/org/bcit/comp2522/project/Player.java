package org.bcit.comp2522.project;

import processing.core.PImage;

import java.awt.*;

public class Player extends Sprite{

  /**
   * Constant for player speed.
   */
  private static final int SPEED = 10;

  /**
   * Constant for player size.
   */
  private static final int PLAYER_IMAGE_SIZE = 100;

  /**
   * The Singleton instance of the Player.
   */
  private static Player player;


  /**
   * Player's hp.
   */
  private int hp;

  /**
   * Player's fire rate.
   */
  private int fireRate;


  /**
   * The time since the player last shot.
   */
  private int shotLast = 0;

  /**
   * player speed.
   */
  private final int speed = 10;

  /**
   * The player's image.
   */
  private PImage playerImage;

  /**
   * The number of fire rate increases the player has.
   */
  private int fireRateIncreases;

  /**
   * The start time of the fire rate decrease.
   */
  private long fireRateDecreaseStartTime;

  /**
   * Private constructor for the Player class.
   *
   * @param x      initial x position of the player
   * @param y      initial y position of the player
   * @param s      size of the player
   * @param window reference to the Window class
   * @param hp     initial hit points of the player
   * @param fr     initial fire rate of the player
   */
  private Player(int x, int y, int s, Window window, int hp, int fr){
    super(x,y,s, window);
    this.hp = hp;
    fireRate = fr;
    isIdle();
  }

  public void isIdle() {
    playerImage = window.loadImage("src/img/playerImgIdle.png");
    playerImage.resize(100,100);
  }

  /**
   * Retrieves the Singleton instance of the Player. If not instantiated, logs an error message.
   *
   * @return the Singleton instance of the Player.
   */
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

  /**
   * Retrieves the Singleton instance of the Player or creates a new instance with the given parameters.
   *
   * @param x      initial x position
   * @param y      initial y position
   * @param s      size of the player
   * @param window the window the player will be drawn in
   * @param hp     initial hit points
   * @param fr     initial fire rate
   * @return the Singleton instance of the Player
   */
  public static Player getInstance(int x, int y, int s, Window window, int hp, int fr) {
    if(player == null) {
      player = new Player(x,y,s,window,hp,fr);
    }
    return player;
  }

  /**
   * @return the x position of the player
   */
  public int getX() {return x;}

  /**
   * @return the y position of the player
   */
  public int getY() {return y;}

  /**
   * @return the hp count of the player
   */
  public int getHp() {return hp;}

  /**
   * @return the fire rate of the player
   */
  public int getFireRate(){return fireRate;}

  /**
   * Sets the x position of the player.
   *
   * @param x the new x position
   */
  public void setX(int x) {this.x = x;}

  /**
   * Sets the y position of the player.
   *
   * @param y the new y position
   */
  public void setY(int y) {this.y = y;}

  /**
   * Sets the hit points of the player.
   *
   * @param hp the new hit points
   */
  public void setHp(int hp) {this.hp = hp;}

  /**
   * Sets the fire rate of the player.
   *
   * @param fr the new fire rate
   */
  public void setFireRate(int fr) {this.fireRate = fr;}

  /**
   * Shoots a bullet from the specified position.
   *
   * @param x the x position of the bullet
   * @param y the y position of the bullet
   */
  private void shoot(int x, int y) {
    BulletManager.getInstance().shootBullet(x,y,-15);
  }

  /**
   * Returns the number of fire rate increases for the player.
   * @return The number of fire rate increases.
   */
  public int getFireRateIncreases() {
    return fireRateIncreases;
  }

  /**
   * Sets the number of fire rate increases for the player.
   * @param fireRateIncreases The new number of fire rate increases.
   */
  public void setFireRateIncreases(int fireRateIncreases) {
    this.fireRateIncreases = fireRateIncreases;
  }

  /**
   * Returns the start time of the fire rate decrease process.
   * @return The start time of the fire rate decrease process.
   */
  public long getFireRateDecreaseStartTime() {
    return fireRateDecreaseStartTime;
  }

  /**
   * Sets the start time of the fire rate decrease process.
   * @param fireRateDecreaseStartTime The new start time for the fire rate decrease process.
   */
  public void setFireRateDecreaseStartTime(long fireRateDecreaseStartTime) {
    this.fireRateDecreaseStartTime = fireRateDecreaseStartTime;
  }

  /**
   * Updates the player's state, including shooting, movement,
   * and changing the player image based on movement direction.
   */
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


  /**
   * Draws the player image on the window using the player's (x, y) coordinates and the appropriate player image
   * based on the player's movement direction.
   */
  @Override
  public void draw() {
    window.pushMatrix();
    window.translate(x, y);
    window.imageMode(window.CENTER);
    window.image(playerImage, 0, 0);
    window.popMatrix();
  }


}

