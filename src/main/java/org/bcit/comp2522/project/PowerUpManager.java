package org.bcit.comp2522.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The PowerUpManager class manages the spawning and updating of power-up objects
 * in the game. It is a singleton class, meaning there can only be one instance
 * of the class in the program. The class is responsible for spawning power-ups
 * at random positions and intervals, updating their positions, and checking for
 * collisions with the player.
 */
public class PowerUpManager {

  /**
   * Singleton instance of the PowerUpManager class.
   */
  private static PowerUpManager instance;

  /**
   * The list of PowerUp objects managed by the PowerUpManager.
   */
  private ArrayList<PowerUp> powerUps;

  /**
   * The time interval between power-up spawns.
   */
  private int spawnTime;

  /**
   * The area in which power-ups can spawn.
   */
  private int spawnArea;

  /**
   * The window in which the power-ups are rendered.
   */
  private Window window;

  /**
   * The time at which the last power-up was spawned.
   */
  private int lastPower;

  /**
   * Counter for fire rate decrease.
   */
  private int fireRateDecreaseCounter = 0;

  /**
   * Constructs a new PowerUpManager object with the specified properties.
   *
   * @param spawnTime The time interval between power-up spawns.
   * @param spawnArea The area in which power-ups can spawn.
   * @param window    The window in which the power-ups will be rendered.
   */
  private PowerUpManager(int spawnTime, int spawnArea, Window window) {
    this.powerUps = new ArrayList<>();
    this.spawnTime = spawnTime;
    lastPower = spawnTime;
    this.spawnArea = spawnArea;
    this.window = window;
  }

  /**
   * Returns the singleton instance of the PowerUpManager class,
   * creating it if it does not already exist.
   *
   * @param spawnTime The time interval between power-up spawns.
   * @param spawnArea The area in which power-ups can spawn.
   * @param window    The window in which the power-ups will be rendered.
   * @return The singleton instance of the PowerUpManager class.
   */
  public static PowerUpManager getInstance(int spawnTime, int spawnArea, Window window) {
    if (instance == null) {
      instance = new PowerUpManager(spawnTime, spawnArea, window);
    }
    return instance;
  }

  /**
   * Returns the singleton instance of the PowerUpManager class.
   *
   * @return The singleton instance of the PowerUpManager class.
   */
  public static PowerUpManager getInstance() {
    return instance;
  }

  public int getLastPower() {
    return lastPower;
  }

  public void setLastPower(int n) {
    lastPower = n;
  }

  /**
   * Returns the list of PowerUp objects managed by the PowerUpManager.
   *
   * @return The list of PowerUp objects.
   */
  public ArrayList<PowerUp> getPowerUp() {
    return powerUps;
  }

  /**
   * Spawns a new PowerUp object at a random position within the spawn area.
   */
  public void spawn() {
    Random random = new Random();
    int xpos = random.nextInt(spawnArea) + window.width / 2 - spawnArea / 2;
    int ypos = 0;
    int size = 5; //to edit
    String type = random.nextBoolean() ? "hp" : "fireRate";
    PowerUp newPowerUp = new PowerUp(xpos, ypos, size, window, type);
    powerUps.add(newPowerUp);
  }

  /**
   * Draws all PowerUp objects managed by the PowerUpManager.
   */
  public void draw() {
    for (PowerUp powerUp : powerUps) {
      powerUp.draw();
    }
  }

  /**
   * Updates the state of all PowerUp objects managed by the PowerUpManager
   * and handles power-up spawning.
   *
   * @param player The player object for which power-ups will be applied.
   */
  public void update(Player player) {
    for (PowerUp powerUp : powerUps) {
      powerUp.update();
    }
    lastPower--;

    // Continuously decrease fire rate once after fireRate power up reaches 4 counts
    if (player.getFireRateIncreases() >= 4
            && System.currentTimeMillis() - player.getFireRateDecreaseStartTime() >= 15000) {
      // Decrease fire rate by the same rate it increased (5 in this case)
      int decreasedFireRate = Math.min(player.getFireRate() + 5, 60);
      player.setFireRate(decreasedFireRate);
      player.setFireRateIncreases(player.getFireRateIncreases() - 1);
      player.setFireRateDecreaseStartTime(0);
    }
    if (lastPower <= 0) {
      spawn();
      lastPower = spawnTime;
    }
  }

  /**
   * Checks for collisions between the player and power-up objects,
   * applying the power-up effect if a collision is detected and removing
   * the power-up object from the list of managed power-ups.
   *
   * @param player The player object to check for collisions.
   * @param lives  The lives manager object to update lives count.
   */
  public void checkCollisions(Player player, LivesManager lives) {
    Iterator<PowerUp> iterator = powerUps.iterator();
    while (iterator.hasNext()) {
      PowerUp powerUp = iterator.next();
      if (Sprite.collided(player, powerUp)) {
        if (powerUp.getType().equals("hp")) {
          lives.gainLife();
        } else if (powerUp.getType().equals("fireRate")) {
          if (player.getFireRateIncreases() < 4) {
            int increasedFireRate = Math.max(player.getFireRate() - 5, 4);
            player.setFireRate(increasedFireRate);
            player.setFireRateIncreases(player.getFireRateIncreases() + 1);
          }
          if (player.getFireRateIncreases() == 4) {
            player.setFireRateDecreaseStartTime(System.currentTimeMillis());
          }
        }
        iterator.remove();
      }
    }
  }

  public void add(PowerUp p) {
    powerUps.add(p);
  }
}
