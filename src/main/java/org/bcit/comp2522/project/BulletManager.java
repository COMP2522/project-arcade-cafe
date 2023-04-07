package org.bcit.comp2522.project;

import java.util.ArrayList;
import java.util.Iterator;

public final class BulletManager {
  private static BulletManager singleton;
  private ArrayList<Bullet> bullets;
  private Window window;
  private int screenWidth;
  private int screenHeight;
  private static final int BULLET_WIDTH = 20;
  private static final int BULLET_HEIGHT = 40;
  private final Object lock = new Object();

  private BulletManager(final Window inGameWindow) {
    this.window = inGameWindow;
    bullets = new ArrayList<>();
    screenWidth = inGameWindow.width;
    screenHeight = inGameWindow.height;
  }

  public static BulletManager getInstance() {
    return singleton;
  }

  public static BulletManager getInstance(final Window window) {
    if (singleton == null) {
      singleton = new BulletManager(window);
    }
    return singleton;
  }

  public void resetBullet(){
    bullets.clear();
  }

  public void shootBullet(final int xpos,
                          final int ypos,
                          final int dy) {
    synchronized (lock) {
      Bullet bullet = new Bullet(xpos,
              ypos - BULLET_HEIGHT,
              BULLET_WIDTH,
              window,
              dy);
      bullets.add(bullet);
    }
  }

  public void add(final Bullet b) {
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

  public boolean isVisible(final int x,
                           final int y) {
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
          // remove bullet from list if it's no longer visible
          iterator.remove();
        }
      }
    }
  }

  public ArrayList<Bullet> getBullets() {
    return bullets;
  }

}
