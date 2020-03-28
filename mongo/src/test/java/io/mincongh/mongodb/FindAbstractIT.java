package io.mincongh.mongodb;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.List;
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
public abstract class FindAbstractIT {
  @Rule public FongoRule fongo = new FongoRule(isRealMongo());

  protected abstract boolean isRealMongo();

  private MongoCollection<BasicDBObject> collection;

  @Before
  public void setUp() {
    var db = fongo.getDatabase();
    db.createCollection("users");
    collection = db.getCollection("users", BasicDBObject.class);
  }

  @Test
  public void find() {
    var foo = new BasicDBObject(Map.of("name", "Foo", "age", 20));
    var bar = new BasicDBObject(Map.of("name", "Bar", "age", 20));

    collection.insertMany(List.of(foo, bar));
    assertThat(collection.find(Filters.eq("name", "Foo"))).containsExactly(foo);
  }
}
