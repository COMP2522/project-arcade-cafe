package org.bcit.comp2522.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The BulletManager class manages the bullets in the game.
 * It tracks the bullets on the screen, updates
 * their positions, and removes them if they go off the screen.
 * It also provides a method for shooting a new bullet.
 *
 * @author Eric Cho
 * @author Samuel Chua
 * @author Mylo Yu
 *
 */
public final class BulletManager {
  private static BulletManager singleton;
  private ArrayList<Bullet> bullets;
  private Window window;
  private int screenWidth;
  private int screenHeight;
  private static final int BULLET_WIDTH = 20;
  private static final int BULLET_HEIGHT = 40;
  private final Object lock = new Object();

  /**
   * Constructs a new BulletManager object with the specified Window.
   *
   * @param inGameWindow the Window object that the bullets will be displayed.
   */
  BulletManager(final Window inGameWindow) {
    this.window = inGameWindow;
    bullets = new ArrayList<>();
    screenWidth = inGameWindow.width;
    screenHeight = inGameWindow.height;
  }

  /**
   * Returns the instance of the BulletManager class.
   *
   * @return the instance of the BulletManager class
   */
  public static BulletManager getInstance() {
    return singleton;
  }

  /**
   * Returns the instance of the BulletManager class with the specified Window.
   *
   * @param window the Window object that the bullets will be displayed in
   * @return the instance of the BulletManager class with the specified Window
   */
  public static BulletManager getInstance(final Window window) {
    if (singleton == null) {
      singleton = new BulletManager(window);
    }
    return singleton;
  }

  /**
   * Clears all bullets from the screen.
   */
  public void resetBullet() {
    bullets.clear();
  }

  /**
   * Sets up audio to play for when the player has fired a bullet.
   *
   * @param soundFilePath specifies what is indicated through the filepath to play audio.
   */
  public static void playShootSound(String soundFilePath) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                                            new File(soundFilePath).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (Exception ex) {
      System.out.println("Error playing firing sound.");
      ex.printStackTrace();
    }
  }

  /**
   * Creates a new Bullet object and adds it to the screen.
   *
   * @param xpos    the x-coordinate of the bullet's starting position
   * @param ypos    the y-coordinate of the bullet's starting position
   * @param dy      the speed at which the bullet will travel
   */
  public void shootBullet(final int xpos,
                          final int ypos,
                          final int dy) {
    synchronized (lock) {
      Bullet bullet = new Bullet(xpos,
              ypos - BULLET_HEIGHT,
              BULLET_WIDTH,
              window,
              dy);
      playShootSound("src/sfx/sfx_shot.wav");
      bullets.add(bullet);
    }
  }

  /**
   * Adds an existing Bullet object to the screen.
   *
   * @param b the Bullet object to add to the screen
   */
  public void add(final Bullet b) {
    bullets.add(b);
  }

  /**
   * Updates the positions of all bullets on the screen, and
   * removes any bullets that have gone off the screen.
   */
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

  /**
   * Determines whether or not a bullet is visible on the screen at the specified coordinates.
   *
   * @param x the x-coordinate to check
   * @param y the y-coordinate to check
   * @return true if the bullet is visible at the specified coordinates, false otherwise
   */
  public boolean isVisible(final int x,
                           final int y) {
    return (x >= 0 && x < screenWidth) && (y >= 0 && y < screenHeight);
  }

  /**
   * Draws all bullets on the screen, and removes any bullets that have gone off the screen.
   */
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

  /**
   * Returns the list of all active bullets in the game.
   *
   * @return the ArrayList of Bullet objects representing all active bullets.
   */
  public ArrayList<Bullet> getBullets() {
    return bullets;
  }

}
