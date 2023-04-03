package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PowerUpManager {
    private static PowerUpManager instance;
    private ArrayList<PowerUp> powerUps;
    private int spawnTime;
    private int spawnArea;
    private Window window;
    private int lastPower;
    private int fireRateDecreaseCounter = 0;

    private PowerUpManager(int spawnTime, int spawnArea, Window window) {
        this.powerUps = new ArrayList<>();
        this.spawnTime = spawnTime;
        lastPower = spawnTime;
        this.spawnArea = spawnArea;
        this.window = window;
    }

    public static PowerUpManager getInstance(int spawnTime, int spawnArea, Window window) {
        if (instance == null) {
            instance = new PowerUpManager(spawnTime, spawnArea, window);
        }
        return instance;
    }
  public static PowerUpManager getInstance() {
    return instance;
  }

    public ArrayList<PowerUp> getPowerUp() {
        return powerUps;
    }


    public void spawn() {
        Random random = new Random();
        int xPos = random.nextInt(spawnArea) + window.width/2 - spawnArea/2;
        int yPos = 0;
        int size = 5; //to edit
        String type = random.nextBoolean() ? "hp" : "fireRate";
        PowerUp newPowerUp = new PowerUp(xPos, yPos, size, window, type);
        powerUps.add(newPowerUp);
    }

    public void draw() {
        for (PowerUp powerUp: powerUps) {
            powerUp.draw();
        }
    }

    public void update(Player player) {
        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }
        lastPower--;

        // Continuously decrease fire rate once after fireRate power up reaches 4 counts
        if (player.getFireRateIncreases() >= 4 && System.currentTimeMillis() - player.getFireRateDecreaseStartTime() >= 15000) {
            int decreasedFireRate = Math.min(player.getFireRate() + 5, 60); // Decrease fire rate by the same rate it increased (5 in this case)
            player.setFireRate(decreasedFireRate);
            player.setFireRateIncreases(player.getFireRateIncreases() - 1);
            player.setFireRateDecreaseStartTime(0);
        }
        if (lastPower <= 0) {
            spawn();
            lastPower = spawnTime;
        }
    }

    public void checkCollisions(Player player, LivesManager lives) {
        Iterator<PowerUp> iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            if (Sprite.collided(player, powerUp)) {
                if (powerUp.getType().equals("hp")) {
                    lives.gainLife();
                } else if (powerUp.getType().equals("fireRate")) {

                    if (player.getFireRateIncreases() < 4) {

                        int increasedFireRate = Math.max(player.getFireRate() - 5, 1);
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


}
