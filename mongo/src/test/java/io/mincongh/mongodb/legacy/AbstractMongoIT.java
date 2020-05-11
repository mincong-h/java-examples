package io.mincongh.mongodb.legacy;

import com.mongodb.MongoClient;
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
    client = new MongoClient("localhost:27017");
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
