package io.mincongh.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test "find" operation in MongoDB.
 *
 * @author Mincong Huang
 */
public abstract class FindAbstractIT {

  private MongoCollection<BasicDBObject> collection;

  protected abstract MongoDatabase database();

  protected void preSetup() {}

  protected void postTeardown() {}

  @Before
  public void setUp() {
    preSetup();

    var db = database();
    db.createCollection("users");
    collection = db.getCollection("users", BasicDBObject.class);
  }

  @After
  public void tearDown() {
    postTeardown();
  }

  @Test
  public void find_eq() {
    var foo = parse("{'name':'Foo', 'age':20}");
    var bar = parse("{'name':'Bar', 'age':20}");
    collection.insertMany(List.of(foo, bar));

    var results = collection.find(Filters.eq("name", "Foo"));
    assertThat(results).containsExactly(foo);
  }

  @Test
  public void find_elemMatch() {
    var foo = parse("{'name':'Foo', 'exams':[{'type':'C1', score:58}, {'type':'C1', score:80}]}");
    var bar = parse("{'name':'Bar', 'exams':[{'type':'B1', score:83}, {'type':'B2', score:85}]}");
    collection.insertMany(List.of(foo, bar));

    var f = Filters.and(Filters.eq("type", "C1"), Filters.lt("score", 60));
    var results = collection.find(Filters.elemMatch("exams", f));
    assertThat(results).containsExactly(foo);

    var results2 = collection.find(Filters.elemMatch("exams", Filters.gt("score", 60)));
    assertThat(results2).containsExactly(foo, bar);
  }

  private BasicDBObject parse(String json) {
    var content = json.replace("'", "\"");
    return BasicDBObject.parse(content);
  }
}
