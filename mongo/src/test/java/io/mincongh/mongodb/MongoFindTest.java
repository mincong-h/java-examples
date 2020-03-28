package io.mincongh.mongodb;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test "find" operation in MongoDB.
 *
 * @author Mincong Huang
 */
// https://mongodb.github.io/mongo-java-driver/4.0/driver/tutorials/databases-collections/
public class MongoFindTest {
  @Rule public FongoRule fongo = new FongoRule();

  private MongoCollection<BasicDBObject> collection;
  private BasicDBObject foo = new BasicDBObject(Map.of("name", "Foo", "age", 20));
  private BasicDBObject bar = new BasicDBObject(Map.of("name", "Bar", "age", 20));

  @Before
  public void setUp() {
    var db = fongo.getDatabase();
    db.createCollection("users");
    collection = db.getCollection("users", BasicDBObject.class);

    collection.insertOne(foo);
    collection.insertOne(bar);
  }

  @Test
  public void find() {
    assertThat(collection.find(Filters.eq("name", "Foo"))).containsExactly(foo);
  }

}
