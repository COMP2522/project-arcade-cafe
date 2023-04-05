package org.bcit.comp2522.project;

import java.util.ArrayList;
import java.util.Iterator;

public class BulletManager {
  private static BulletManager singleton;
  private ArrayList<Bullet> bullets;
  private Window window;
  private int screenWidth;
  private int screenHeight;
  private final Object lock = new Object();

  private BulletManager(Window window) {
    this.window = window;
    bullets = new ArrayList<>();
    screenWidth = window.width;
    screenHeight = window.height;
  }

  public static BulletManager getInstance(){
    return singleton;
  }
  public static BulletManager getInstance(Window window){
    if(singleton == null) {
      singleton = new BulletManager(window);
    }
    return singleton;
  }

  public void shootBullet(int xPos, int yPos, int dy) {
    synchronized (lock) {
      Bullet bullet = new Bullet(xPos, yPos - 40, 20, window, dy);
      bullets.add(bullet);
    }
  }

  public void add(Bullet b) {
    bullets.add(b);
  }

  public void update() {
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

  public void draw() {
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
