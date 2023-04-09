package org.bcit.comp2522.project;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * The SaveHandler class is responsible for handling
 * the saving and parsing of the game state. It utilizes the LevelManager class
 * to write the current game state to a JSON file and read it back in
 * for loading a previously saved game. The class catches exceptions
 * that may occur during the saving or parsing process and
 * throws a RuntimeException if an exception occurs.
 *
 * @author Mylo Yu
 *
 */
public class SaveHandler {

  /**
   * Saves the current game state to a JSON file.
   * The LevelManager is used to write the game state to the file specified
   * by the file name "save.json". If the file is not found, a FileNotFoundException
   * is caught and a RuntimeException is thrown.
   *
   * @author Mylo Yu
   * @author Eric Cho
   * @author Samuel Chua
   *
   */
  public void saveState() {
    try {
      LevelManager.getInstance().writeToFile("save.json");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Parses the previously saved game state from a JSON file.
   * The LevelManager is used to read the game state from
   * the file specified by the file name "save.json".
   * If the file cannot be read or the parsing process fails,
   * an IOException or ParseException is caught,
   * respectively, and a RuntimeException is thrown.
   */
  public void parseSave() {
    try {
      LevelManager.getInstance().readFromFile("save.json");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }


}
