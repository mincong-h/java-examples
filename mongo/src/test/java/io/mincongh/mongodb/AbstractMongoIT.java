package io.mincongh.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.After;
import org.junit.Before;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public abstract class AbstractMongoIT {

  protected MongoClient client;

  @Before
  public void setUp() {
    client = MongoClients.create("mongodb://localhost:27017");
  }

  @After
  public void tearDown() {
    client.close();
  }

  protected abstract Class<?> getTestClass();

  protected String collectionName() {
    return "collectionFor" + getTestClass().getSimpleName();
  }
}
