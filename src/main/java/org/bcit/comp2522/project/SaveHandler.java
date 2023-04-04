package org.bcit.comp2522.project;


import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SaveHandler {
  public void saveState() {
    try {
      LevelManager.getInstance().writeToFile("save.json");
    } catch (FileNotFoundException e){
      throw new RuntimeException(e);
    }
  }

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
