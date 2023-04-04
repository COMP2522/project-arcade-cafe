package org.bcit.comp2522.project;


import java.io.FileNotFoundException;

public class SaveHandler {
  public void saveState() {
    try {
      LevelManager.getInstance().writeToFile("src/data/save.json");
    } catch (FileNotFoundException e){
      throw new RuntimeException(e);
    }
  }

}
