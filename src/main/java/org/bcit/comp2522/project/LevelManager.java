package org.bcit.comp2522.project;

import static processing.core.PApplet.dist;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The LevelManager class manages the game state, updates and draws all game objects.
 * Implements the Singleton design pattern.
 * Responsible for updating the player, enemies, bullets, power-ups and score.
 * Handles collisions between objects, manages the player's lives and high score.
 *
 * @author Sungmok Cho, Samuel Chua, Helen Liu, Sunmin Park, Mylo Yu
 * @version 2023-04-05
 */
public class LevelManager {
  private static LevelManager lm;
  public boolean paused = false;
  private Player player;
  private EnemyManager em;
  private BulletManager bm;
  private PowerUpManager pm;
  private LivesManager lives;
  private ScoreManager sc;
  private MenuManager menuManager;
  private DatabaseHandler db;
  private int highscore = 0;

  private GameState gameState;

  private LevelManager() {
    em = EnemyManager.getInstance();
    bm = BulletManager.getInstance();
    pm = PowerUpManager.getInstance();
    player = Player.getInstance();
    gameState = GameState.MAIN_MENU;
    lives = new LivesManager(player, player.window, 3); // set initial HP to 1
    sc = ScoreManager.getInstance(player.window);
    menuManager = new MenuManager(player.window, this::setState);

    ObjectMapper mapper = new ObjectMapper();
    DatabaseHandler.Config config;
    try {
      config = mapper.readValue(new File(
                               "src/main/java/org/bcit/comp2522/project/config.json"),
                                DatabaseHandler.Config.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Use the values from the Config object to create the DatabaseHandler
    db = new DatabaseHandler(config.getDB_USERNAME(), config.getDB_PASSWORD());
  }

  public void setState(GameState newState) {
    this.gameState = newState;
  }

  /**
   * Returns the single instance of the LevelManager class.
   * If the instance has not been created yet, it creates a new instance.
   * This method ensures that there is only one instance of the LevelManager class in the game.
   *
   * @return The single instance of the LevelManager class.
   */
  public static LevelManager getInstance() {
    if (lm == null) {
      lm = new LevelManager();
    }
    return lm;
  }

  public GameState getState() {
    return gameState;
  }

  public boolean getPauseStatus() {
    return paused;
  }

  /**
   * Pauses or unpauses the game depending on the current pause status.
   */
  public void pause() {
    if (paused) {
      paused = false;
      gameState = GameState.PLAYING;
    } else {
      paused = true;
      gameState = GameState.PAUSED;
    }
  }

  private boolean gameOver = false;

  public boolean isGameOver() {
    return gameOver;
  }

  public void resetGameOver() {
    this.gameOver = false;
  }

  /**
   * This method is responsible for updating the screen with the current state of the game.
   * Draws all the game objects to the screen.
   */
  public void draw() {
    if (gameState == GameState.MAIN_MENU
        || gameState == GameState.GAME_OVER
        || gameState == GameState.SCORE_BOARD) {
      menuManager.draw(gameState);
    } else {
      em.draw();
      bm.draw();
      pm.draw();
      player.draw();
      lives.draw();
      sc.draw();
      if (gameState == GameState.PAUSED) {
        menuManager.draw(GameState.PAUSED);
      }
    }
  }

  /**
   * This method is responsible for updating the objects and detecting collisions between them.
   * Updates the state of the game objects and checks for collisions.
   */
  public void update() {
    if (!paused) {
      em.update();
      bm.update();
      pm.update(player);
      player.update();
      checkBulletCollisions(bm, em, pm);
      pm.checkCollisions(player, lives);

      if (player.getHp() == 0) {
        highscore = ScoreManager.getInstance(player.window).getScore();
        System.out.println(highscore);
        File file = new File("save.json");
        if (file.exists()) {
          file.delete();
        }
        db.put("score", getHighscore());
        setState(GameState.GAME_OVER);
        resetGame();
        //        Mm.setState(); // Set the state to 3 (game over)
        //        gameOver = true; // Update the gameOver flag to true
      }

      ArrayList<Enemy> copy = new ArrayList<>(em.getEnemy());
      for (Enemy enemy : copy) {
        if (playerCollidesWithEnemy(enemy, player)) {
          // Remove the enemy from the list
          em.removeEnemy(enemy);
          // Player has been hit by an enemy, decrease HP
          // You could also add other effects, like playing a sound or showing an animation
          System.out.println("Player hit by enemy, HP decreased by 1");
        }
      }
    }
  }

  /**
   * getter for high score.
   *
   * @return highscore
   */
  public int getHighscore() {
    return this.highscore;
  }

  /**
   * This method resets the game objects to their initial values.
   * Resets the game state to its initial values.
   */
  public void resetGame() {
    File file = new File("save.json");
    if (file.exists()) {
      file.delete();
    }
    resetGameOver();
    resetPlayerLives();
    sc.resetScore();
    em.resetEnemy();
  }

  /**
   * Writes the current game state to a JSON file with the specified file name.
   *
   * @param file the name of the file to write to
   * @throws FileNotFoundException f the file cannot be found or written to
   */
  public void writeToFile(String file) throws FileNotFoundException {
    JSONObject jo = new JSONObject();
    //storing score
    jo.put("score", sc.getScore());

    //storing time since last powerup
    jo.put("lastPower", pm.getLastPower());

    //storing player info
    JSONObject playerStats = new JSONObject();
    playerStats.put("x", player.getX());
    playerStats.put("y", player.getY());
    playerStats.put("hp", player.getHp());
    playerStats.put("fireRate", player.getFireRate());
    jo.put("player", playerStats);

    //storing bullet info
    JSONArray bullets = new JSONArray();
    ArrayList<Bullet> bulletList = bm.getBullets();
    for (Bullet b : bulletList) {
      JSONObject bulletStats = new JSONObject();
      bulletStats.put("x", b.getX());
      bulletStats.put("y", b.getY());
      bulletStats.put("speed", b.getSpeed());
      bullets.add(bullets.size(), bulletStats);
    }
    jo.put("bullets", bullets);

    //storing enemies
    JSONArray enemies = new JSONArray();
    ArrayList<Enemy> enemyList = em.getEnemy();
    for (Enemy e : enemyList) {
      JSONObject enemyStats = new JSONObject();
      enemyStats.put("x", e.getX());
      enemyStats.put("y", e.getY());
      enemies.add(enemies.size(), enemyStats);
    }
    jo.put("enemies", enemies);

    //storing powerups
    JSONArray powerups = new JSONArray();
    ArrayList<PowerUp> powerupList = pm.getPowerUp();
    for (PowerUp p : powerupList) {
      JSONObject powerupStats = new JSONObject();
      powerupStats.put("x", p.getX());
      powerupStats.put("y", p.getY());
      powerupStats.put("type", p.getType());
      powerups.add(powerups.size(), powerupStats);
    }
    jo.put("powerups", powerups);

    //storing enemy manager info
    JSONObject emStats = new JSONObject();
    emStats.put("wave", em.getNumRows());
    emStats.put("yStart", em.getYStart());
    jo.put("enemyManager", emStats);

    PrintWriter pw = new PrintWriter(file);
    pw.write(jo.toJSONString());
    pw.flush();
    pw.close();
    System.out.println("Game State Saved");


  }

  /**
   * Reads game state data from a JSON file and updates the game accordingly.
   *
   * @param file the path to the JSON file to read from
   * @throws IOException if an I/O error occurs while reading the file
   * @throws ParseException if the JSON file is incorrectly formatted
   */
  public void readFromFile(String file) throws IOException, ParseException {
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader(file)); //the location of the file
    JSONObject jsonObject = (JSONObject) obj;
    //parse score and time since last powerup
    sc.increaseScore(Long.valueOf((long) jsonObject.get("score")).intValue());
    pm.setLastPower(Long.valueOf((long) jsonObject.get("lastPower")).intValue());

    //parse player stats
    JSONObject playerStats = (JSONObject) jsonObject.get("player");
    player.setX(Long.valueOf((long) playerStats.get("x")).intValue());
    player.setY(Long.valueOf((long) playerStats.get("y")).intValue());
    player.setHp(Long.valueOf((long) playerStats.get("hp")).intValue());
    player.setFireRate(Long.valueOf((long) playerStats.get("fireRate")).intValue());

    //parsing bullet info
    JSONArray bullets = (JSONArray) jsonObject.get("bullets");
    for (int i = 0; i < bullets.size(); i++) {
      JSONObject bulletInfo = (JSONObject) bullets.get(i);
      Bullet bullet = new Bullet(
              Long.valueOf((long) bulletInfo.get("x")).intValue(),
              Long.valueOf((long) bulletInfo.get("y")).intValue(),
              20, player.getWindow(), Long.valueOf((long) bulletInfo.get("speed")).intValue());
      bm.add(bullet);
    }

    //parsing enemies
    JSONArray enemies = (JSONArray) jsonObject.get("enemies");
    for (int i = 0; i < enemies.size(); i++) {
      JSONObject enemyInfo = (JSONObject) enemies.get(i);
      Enemy enemy = new Enemy(Long.valueOf((long) enemyInfo.get("x")).intValue(),
              Long.valueOf((long) enemyInfo.get("y")).intValue(),
              30, player.getWindow());
      em.add(enemy);
    }

    //parsing powerups
    JSONArray powerups = (JSONArray) jsonObject.get("powerups");
    for (int i = 0; i < powerups.size(); i++) {
      JSONObject powerupInfo = (JSONObject) powerups.get(i);
      PowerUp powerup = new PowerUp(Long.valueOf((long) powerupInfo.get("x")).intValue(),
              Long.valueOf((long) powerupInfo.get("y")).intValue(),
              5, player.getWindow(), (String) powerupInfo.get("type"));
      pm.add(powerup);
    }

    //parsing enemy manager info
    JSONObject emStats = (JSONObject) jsonObject.get("enemyManager");
    em.setWave(Long.valueOf((long) emStats.get("wave")).intValue());
    em.setYStart(Long.valueOf((long) emStats.get("yStart")).intValue());
    System.out.println("Parsing Complete");
    gameState = GameState.PLAYING;
  }

  /**
   * Reads the score value from a JSON file.
   *
   * @param file the location of the JSON file to read from
   * @return the score value read from the file as an integer
   * @throws IOException if there is an error reading the file
   * @throws ParseException if there is an error parsing the JSON file
   */
  public int readScoreFromFile(String file) throws IOException, ParseException {
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader(file)); //the location of the file
    JSONObject jsonObject = (JSONObject) obj;
    // Get the score value from the JSON object
    long scoreValue = (long) jsonObject.get("score");

    return (int) scoreValue;
  }

  public void resetPlayerLives() {
    player.setHp(3); // Set the initial HP to 3 or any other value you prefer
  }

  /**
   * Checks for collisions between bullets and enemies,
   * removes the bullet and enemy if they collide,
   * and increases the player's score.
   *
   * @param bulletManager the BulletManager object that manages bullets in the game
   * @param enemyManager the EnemyManager object that manages enemies in the game
   * @param powerUpManager the PowerUpManager object that manages power-ups in the game
   */
  public void checkBulletCollisions(BulletManager bulletManager,
                                    EnemyManager enemyManager,
                                    PowerUpManager powerUpManager) {
    ArrayList<Bullet> bullets = bulletManager.getBullets();
    ArrayList<Enemy> enemies = enemyManager.getEnemy();
    ArrayList<PowerUp> powerUps = powerUpManager.getPowerUp();

    Iterator<Bullet> bulletIterator = bullets.iterator();
    while (bulletIterator.hasNext()) {
      Bullet bullet = bulletIterator.next();

      Iterator<Enemy> enemyIterator = enemies.iterator();
      while (enemyIterator.hasNext()) {
        Enemy enemy = enemyIterator.next();

        if (bulletCollidesWithEnemy(bullet, enemy)) {
          enemyIterator.remove(); // remove the enemy if it collided with a bullet
          bulletIterator.remove(); // remove the bullet if it collided with an enemy
          sc.increaseScore(1); // increase the score by 1
          highscore++;
          break;
        }
      }
    }
  }

  /**
   * Checks if a given bullet collides with a given enemy.
   *
   * @param bullet the bullet to check for collision
   * @param enemy the enemy to check for collision
   * @return true if the bullet collides with the enemy, false otherwise
   */
  public boolean bulletCollidesWithEnemy(Bullet bullet, Enemy enemy) {
    float distance = dist(bullet.getX(), bullet.getY(), enemy.getX(), enemy.getY());
    float minDistance = (bullet.getSize() + enemy.getSize()) / 2;
    return distance <= minDistance;
  }

  /**
   * Checks if a given enemy collides with the player.
   *
   * @param enemy the enemy to check for collision
   * @param player the player to check for collision
   * @return true if the enemy collides with the player, false otherwise
   */
  public boolean playerCollidesWithEnemy(Enemy enemy, Player player) {
    float distance = dist(player.getX(), player.getY(), enemy.getX(), enemy.getY());
    float minDistance = (player.getSize() + enemy.getSize()) / 2;

    if (distance <= minDistance) {
      player.setHp(player.getHp() - 1);
      return true;
    }
    return false;
  }

}
