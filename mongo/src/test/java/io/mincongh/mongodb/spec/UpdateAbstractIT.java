package io.mincongh.mongodb.spec;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.List;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test "update" operation in MongoDB.
 *
 * @author Mincong Huang
 */
public abstract class UpdateAbstractIT {

  protected abstract MongoDatabase database();

  protected void preSetup() {}

  protected void postTeardown() {}

  private MongoCollection<BasicDBObject> collection;

  private BasicDBObject foo;

  private BasicDBObject bar;

  @Before
  public void setUp() {
    preSetup();

    var db = database();
    db.createCollection("users");
    collection = db.getCollection("users", BasicDBObject.class);

    foo = parse("{'name':'Foo', 'exams':[{'type':'C1', score:58}, {'type':'C1', score:80}]}");
    bar = parse("{'name':'Bar', 'exams':[{'type':'B1', score:83}, {'type':'B2', score:85}]}");
    collection.insertMany(List.of(foo, bar));
  }

  @After
  public void teadDown() {
    postTeardown();
  }

  @Test
  public void update_eq() {
    // When updating user name Foo to FooFoo
    var filter = Filters.eq("name", "Foo");
    var update = Updates.set("name", "FooFoo");
    var result = collection.updateOne(filter, update);

    // Then the update is acknowledged
    assertThat(result.getMatchedCount()).isEqualTo(1L);
    assertThat(result.getModifiedCount()).isEqualTo(1L);
    assertThat(result.getUpsertedId()).isNull();

    // And the object is updated in database
    var newFoo = new BasicDBObject(foo);
    newFoo.replace("name", "FooFoo");
    assertThat(collection.find()).containsExactlyInAnyOrder(newFoo, bar);
  }

  /**
   * Update one single field under the positional '$' operator.
   *
   * <pre>
   * { "exams.$.score" : value }
   * </pre>
   *
   * @see <a href="https://docs.mongodb.com/v3.6/reference/operator/update/positional/">$ (update) -
   *     Mongo Manual</a>
   */
  @Test
  public void update_elemMatch_elementField() {
    // When updating the user having exam score under 60 to 60
    var elemFilter = Filters.and(Filters.eq("type", "C1"), Filters.lt("score", 60));
    var filter = Filters.elemMatch("exams", elemFilter);
    var update = Updates.set("exams.$.score", 60);
    var result = collection.updateOne(filter, update);

    // Then the update is acknowledged
    assertThat(result.getMatchedCount()).isEqualTo(1L);
    assertThat(result.getModifiedCount()).isEqualTo(1L);
    assertThat(result.getUpsertedId()).isNull();

    // And the object is updated in database
    var newFoo = collection.find(Filters.eq("name", "Foo")).first();
    var exams = (BasicDBList) newFoo.get("exams");
    assertThat(exams)
        .containsExactly(parse("{'type':'C1', score:60}"), parse("{'type':'C1', score:80}"));
  }

  /**
   * Update entire element (object) under the positional '$' operator.
   *
   * <pre>
   * { "exams.$" : value }
   * </pre>
   *
   * @see <a href="https://docs.mongodb.com/v3.6/reference/operator/update/positional/">$ (update) -
   *     Mongo Manual</a>
   */
  @Test
  public void update_elemMatch_element() {
    // When updating the user having exam score under 60 to 60
    var elemFilter = Filters.and(Filters.eq("type", "C1"), Filters.lt("score", 60));
    var filter = Filters.elemMatch("exams", elemFilter);
    var update = Updates.set("exams.$", parse("{'type':'C1', score:60}"));
    var result = collection.updateOne(filter, update);

    // Then the update is acknowledged
    assertThat(result.getMatchedCount()).isEqualTo(1L);
    assertThat(result.getModifiedCount()).isEqualTo(1L);
    assertThat(result.getUpsertedId()).isNull();

    // And the object is updated in database
    var newFoo = collection.find(Filters.eq("name", "Foo")).first();
    var exams = (BasicDBList) newFoo.get("exams");
    assertThat(exams)
        .containsExactly(parse("{'type':'C1', score:60}"), parse("{'type':'C1', score:80}"));
  }

  private BasicDBObject parse(String json) {
    var content = json.replace("'", "\"");
    return BasicDBObject.parse(content);
  }
}
