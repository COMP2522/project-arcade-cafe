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
        Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)); //to edit
        String type = random.nextBoolean() ? "hp" : "fireRate";
        PowerUp newPowerUp = new PowerUp(xPos, yPos, size, color, window, type);
        powerUps.add(newPowerUp);
    }

    public void draw() {
        for (PowerUp powerUp: powerUps) {
            powerUp.draw();
        }
    }

    public void update() {
        for (PowerUp powerUp: powerUps) {
            powerUp.update();
        }
        lastPower--;
        if(lastPower <= 0){
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
                int increasedFireRate = Math.max(player.getFireRate() - 2, 1); //Decreases the fire rate value to increase firing speed
                player.setFireRate(increasedFireRate);
            }
            iterator.remove();
        }
    }
}


}





