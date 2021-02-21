package io.mincongh.mongodb.legacy;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class MongoCollectionIT extends AbstractMongoIT {

  private MongoDatabase db;

  @Override
  protected Class<?> getTestClass() {
    return MongoCollectionIT.class;
  }

  @Override
  @Before
  public void setUp() {
    super.setUp();
    db = client.getDatabase("local");
  }

  @Override
  @After
  public void tearDown() {
    db.getCollection(collectionName()).drop();
    super.tearDown();
  }

  @Test
  public void createCollection() {
    /*
     * MongoDB stores documents in collections. Collections are analogous to
     * tables in relational database.
     *
     * If a collection does not exist, MongoDB creates the collection when you
     * first store data for that collections.
     *
     * MongoDB provides the db.createCollection() method to explicitly create a
     * collection with options, such as setting the maximum size or the
     * documentation validation rules. If you are not specifying these options,
     * you do not need to explicitly create the collection since MongoDB
     * creates new collections when you first store data for the collections.
     */
    db = client.getDatabase("local");
    db.createCollection(collectionName());
    MongoCollection<Document> collection = db.getCollection(collectionName());
    assertThat(collection.countDocuments()).isZero();
  }
}
