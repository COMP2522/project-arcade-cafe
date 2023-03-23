package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BulletManager {
  private ArrayList<Bullet> bullets;
  private Window window;

  public BulletManager(Window window) {
    this.window = window;
    bullets = new ArrayList<>();
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        updateBullets();
      }
    }, 0, 16); // Update the bullets every 16 milliseconds
  }

  public void shootBullet(int xPos, int yPos, int dy) {
    Bullet bullet = new Bullet(xPos, yPos, 20, new Color(255, 255, 0), window, dy);
    bullets.add(bullet);
  }

  public void updateBullets() {
    for (Bullet bullet : bullets) {
      bullet.update();
    }
  }

  public void drawBullets() {
    for (Bullet bullet : bullets) {
      bullet.draw();
    }
  }
  public ArrayList<Bullet> getBullets() {
    return bullets;
  }
}

