package org.bcit.comp2522.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseHandler {
  MongoDatabase database;
  String myCollection;
  public DatabaseHandler(String username, String password) {
    ConnectionString connectionString = new ConnectionString(
            String.format("mongodb+srv://%s:%s@cluster0.t1r3fam.mongodb.net/?retryWrites=true&w=majority", username, password));
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    this.database = mongoClient.getDatabase("test");
    this.myCollection = "new";
    try {
      this.database.createCollection((this.myCollection));
    } catch (Exception e) {
      System.out.println("Collection alreday exists");
    }
  }

  public void put(String key, String value) {
    Document document = new Document();
    document.append(key,value);
    new Thread(()->database.getCollection("new").insertOne(document)).start();
  }

  public static class Config {
    private String DB_USERNAME;
    private String DB_PASSWORD;

    @JsonProperty("DB_USERNAME")
    public String getDB_USERNAME() {
      return DB_USERNAME;
    }

    @JsonProperty("DB_PASSWORD")
    public String getDB_PASSWORD() {
      return DB_PASSWORD;
    }

    public void setDB_USERNAME(String DB_USERNAME) {
      this.DB_USERNAME = DB_USERNAME;
    }

    public void setDB_PASSWORD(String DB_PASSWORD) {
      this.DB_PASSWORD = DB_PASSWORD;
    }
  }



  public static void main(String[] args) {
    ObjectMapper mapper = new ObjectMapper();
    Config config;
    try {
      config = mapper.readValue(new File("/Users/sungmokcho/BCIT/COMP2522/team/project-arcade-cafe/src/main/java/org/bcit/comp2522/project/config.json"), Config.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // Use the values from the Config object to create the DatabaseHandler
    DatabaseHandler db = new DatabaseHandler(config.getDB_USERNAME(), config.getDB_PASSWORD());
    db.put("test1", "teet1");
    Document find = db.database
            .getCollection("new")
            .find(eq("Hello", "world"))
            .first();

    System.out.println(find);
  }
}