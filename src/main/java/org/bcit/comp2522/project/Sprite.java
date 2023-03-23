package org.bcit.comp2522.project;

import processing.core.PApplet;

import java.awt.*;

public class Sprite {
  protected int x;
  protected int y;
  protected int size;
  protected Color color;
  protected Window window;

  public Sprite(int xPos, int yPos, int size, Color color, Window window) {
    this.x = xPos;
    this.y = yPos;
    this.size = size;
    this.color = color;
    this.window = window;
  }

  public int getX() {
    return this.x;
  }
  public void setX(int xPos) {
    this.x = xPos;
  }
  public int getY() {
    return this.y;
  }
  public void setY(int yPos) {
    this.y = yPos;
  }
  public int getSize() {
    return size;
  }
  public void draw() {
    window.pushStyle();
    window.fill(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
    window.ellipse(this.x, this.y, size, size);
    window.popStyle();
  }
  public void move(int dx, int dy) {
    this.x += dx;
    this.y += dy;
  }
  public static boolean collided(Sprite a, Sprite b) {
    float distance = (float) Math.sqrt((b.getX() - a.getX())^2 - (b.getY() - a.getY())^2);
    if (distance <= (a.getSize() + b.getSize())) {
      return true;
    }
    return false;
  }

  public Window getWindow() {
    return window;
  }

  public PApplet getPApplet() {
    return window;
  }
}