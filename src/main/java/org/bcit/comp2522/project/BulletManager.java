package org.bcit.comp2522.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class BulletManager {
  private ArrayList<Bullet> bullets;
  private Window window;
  private int screenWidth;
  private int screenHeight;
  private final Object lock = new Object();

  public BulletManager(Window window) {
    this.window = window;
    bullets = new ArrayList<>();
    screenWidth = window.width;
    screenHeight = window.height;
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        updateBullets();
      }
    }, 0, 5); // Update the bullets every 16 milliseconds
  }

  public void shootBullet(int xPos, int yPos, int dy) {
    synchronized (lock) {
      Bullet bullet = new Bullet(xPos, yPos, 20, new Color(255, 255, 0), window, dy);
      bullets.add(bullet);
    }
  }

  public void updateBullets() {
    synchronized (lock) {
      Iterator<Bullet> iter = bullets.iterator();
      while (iter.hasNext()) {
        Bullet bullet = iter.next();
        bullet.update();
        if (bullet.getY() < 0) {
          iter.remove(); // remove bullet from list using iterator
        }
      }
    }
  }

  public boolean isVisible(int x, int y) {
    return (x >= 0 && x < screenWidth) && (y >= 0 && y < screenHeight);
  }

  public void drawBullets() {
    synchronized (lock) {
      Iterator<Bullet> iterator = bullets.iterator();
      while (iterator.hasNext()) {
        Bullet bullet = iterator.next();
        if (isVisible(bullet.getX(), bullet.getY())) {
          bullet.draw();
        } else {
          iterator.remove(); // remove bullet from list if it's no longer visible
        }
      }
    }
  }

  public ArrayList<Bullet> getBullets() {
    return bullets;
  }

}
