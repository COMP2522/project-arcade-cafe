package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PowerUpManager {
    private static PowerUpManager instance;
    private ArrayList<PowerUp> powerUps;
    private int spawnTime;
    private int spawnArea;
    private Window window;
    private Timer powerUpTimer;

    private PowerUpManager(int spawnTime, int spawnArea, Window window) {
        this.powerUps = new ArrayList<>();
        this.spawnTime = spawnTime;
        this.spawnArea = spawnArea;
        this.window = window;

        powerUpTimer = new Timer();
        powerUpTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spawn();
            }
        }, 15000, 15000); // Generate a powerup every 15 seconds (15000 milliseconds)
    }

    public static PowerUpManager getInstance(int spawnTime, int spawnArea, Window window) {
        if (instance == null) {
            instance = new PowerUpManager(spawnTime, spawnArea, window);
        }
        return instance;
    }

    public void spawn() {
        Random random = new Random();
        int xPos = random.nextInt(spawnArea);
        int yPos = random.nextInt(spawnArea);
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
    }

//    public void checkCollisions(Player player) {
//        for (int i = 0; i < powerUps.size(); i++) {
//            PowerUp powerUp = powerUps.get(i);
//            if (Sprite.collided(player, powerUp)) {
//                if (powerUp.getType().equals("hp")) {
//                    player.setHp(player.getHp()+50);
//                } else if (powerUp.getType().equals("fireRate")) {
//                    int increasedFireRate = Math.max(player.getFireRate() - 5, 1); //Decreases the fire rate value to increase firing speed
//                    player.setFireRate(increasedFireRate);
//                }
//                powerUps.remove(i);
//                i--;
//            }
//        }
//    }

//    public void checkCollisions(Sprite sprite) {
//        Iterator<PowerUp> iterator = powerUps.iterator();
//        while (iterator.hasNext()) {
//            PowerUp powerUp = iterator.next();
//            if (Sprite.collided(sprite, powerUp)) {
//                if (sprite instanceof Player) {
//                    Player player = (Player) sprite;
//                    if (powerUp.getType().equals("hp")) {
//                        player.setHp(player.getHp() + 50);
//                    } else if (powerUp.getType().equals("fireRate")) {
//                        int increasedFireRate = Math.max(player.getFireRate() - 5, 1); //Decreases the fire rate value to increase firing speed
//                        player.setFireRate(increasedFireRate);
//                    }
//                    iterator.remove();
//                }
//            }
//        }
//    }

    public void checkCollisions(Player player, BulletManager bulletManager) {
        Iterator<PowerUp> iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            if (Sprite.collided(player, powerUp)) {
                if (powerUp.getType().equals("hp")) {
                    player.setHp(player.getHp() + 50);
                } else if (powerUp.getType().equals("fireRate")) {
                    int increasedFireRate = Math.max(player.getFireRate() - 5, 1); //Decreases the fire rate value to increase firing speed
                    player.setFireRate(increasedFireRate);
                }
                iterator.remove();
            } else {
                // Check for collisions with bullets
                for (Bullet bullet : bulletManager.getBullets()) {
                    if (Sprite.collided(bullet, powerUp)) {
                        // If a collision is detected, break the loop and continue checking the next power-up
                        break;
                    }
                }
            }
        }
    }
}





