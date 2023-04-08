package org.bcit.comp2522.project;

import processing.core.PImage;


public class Player extends Sprite{

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
   * @param xpos      initial x position of the player
   * @param ypos      initial y position of the player
   * @param size      size of the player
   * @param window reference to the Window class
   * @param hp     initial hit points of the player
   * @param fireRate     initial fire rate of the player
   */
  private Player(int xpos, int ypos, int size, Window window, int hp, int fireRate){
    super(xpos, ypos, size, window);
    this.hp = hp;
    this.fireRate = fireRate;
    isIdle();
  }

  public void isIdle() {
    playerImage = window.loadImage("src/img/playerImgIdle.png");
    playerImage.resize(100,100);
  }

  /**
   * Retrieves the Singleton instance of the Player. If not instantiated, logs an error message.
   *
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
    switch (animCase) {
      case "Idle" -> isIdle();
      case "MovingLeft" -> isMovingLeft();
      case "MovingRight" -> isMovingRight();
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
   * @param xpos      initial x position
   * @param ypos      initial y position
   * @param size      size of the player
   * @param window the window the player will be drawn in
   * @param hp     initial hit points
   * @param fireRate     initial fire rate
   * @return the Singleton instance of the Player
   */
  public static Player getInstance(int xpos, int ypos, int size, Window window, int hp, int fireRate) {
    if(player == null) {
      player = new Player(xpos, ypos, size, window,hp,fireRate);
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
   * @return the time since last shot of the player
   */
  public int getShotLast(){return shotLast;}

  /**
   * @return the fire rate of the player
   */
  public int getFireRate(){return fireRate;}

  /**
   * Sets the x position of the player.
   *
   * @param xpos the new x position
   */
  public void setX(int xpos) {this.x = xpos;}

  /**
   * Sets the y position of the player.
   *
   * @param ypos the new y position
   */
  public void setY(int ypos) {this.y = ypos;}

  /**
   * Sets the hit points of the player.
   *
   * @param hp the new hit points
   */
  public void setHp(int hp) {this.hp = hp;}

  /**
   * Sets the fire rate of the player.
   *
   * @param shotLast the new fire rate
   */
  public void setShotLast(int shotLast) {this.shotLast = shotLast;}

  /**
   * Sets the fire rate of the player.
   *
   * @param fireRate the new fire rate
   */
  public void setFireRate(int fireRate) {this.fireRate = fireRate;}

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

    int speed = 10;
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

