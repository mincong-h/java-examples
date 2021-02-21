package io.mincongh.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.After;
import org.junit.Before;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public abstract class AbstractMongoIT {

  protected MongoClient client;
  protected MongoDatabase db;

  @Before
  public void setUp() {
    client = MongoClients.create("mongodb://localhost:27017");
    db = client.getDatabase("test");
  }

  @After
  public void tearDown() {
    try {
      db.drop();
    } finally {
      client.close();
    }
  }
}
