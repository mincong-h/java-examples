package io.mincongh.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public abstract class AbstractMongoIT {

  protected MongoClient client;
  protected MongoDatabase db;

  @BeforeEach
  void setUpMongo() {
    client = MongoClients.create("mongodb://localhost:27017");
    db = client.getDatabase("test");
  }

  @AfterEach
  void tearDownMongo() {
    try {
      db.drop();
    } finally {
      client.close();
    }
  }
}
