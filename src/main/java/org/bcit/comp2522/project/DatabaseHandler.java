package org.bcit.comp2522.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.bson.Document;

public class DatabaseHandler {
  MongoDatabase database;
  String myCollection;

  public DatabaseHandler(String username, String password) {
    ConnectionString connectionString = new ConnectionString(
            String.format("mongodb+srv://%s:%s@cluster0.t1r3fam.mongodb.net"
                    + "/?retryWrites=true&w=majority", username, password));
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    this.database = mongoClient.getDatabase("Arcade_cafe");
    this.myCollection = "score";
  }

  public void put(final String key, final int value) {
    Document document = new Document();
    document.append(key, value);
    document.append("date", LocalDateTime.now().toString());
    database.getCollection(myCollection).insertOne(document);
    System.out.println("Successfully stored in DB!");
  }

  public static class Config {
    private String dbUsername;
    private String dbPassword;

    @JsonProperty("DB_USERNAME")
    public String getDbUsername() {
      return dbUsername;
    }

    @JsonProperty("DB_PASSWORD")
    public String getDbPassword() {
      return dbPassword;
    }
  }

  public ArrayList<Document> getTopScores() {
    ArrayList<Document> topScores = new ArrayList<>();

    // Find the top 10 scores by sorting
    // the collection by score in descending order
    database.getCollection(myCollection)
        .find()
        .sort(new Document("score", -1).append("_id", 1))
        .limit(10)
        .forEach((Consumer<Document>) topScores::add);

    return topScores;
  }
}
