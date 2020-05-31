package io.mincongh.mongodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeFalse;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import io.mincongh.mongodb.utils.MongoProvider;
import io.mincongh.mongodb.utils.MongoProviderFactory;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test "update" operation in MongoDB.
 *
 * @author Mincong Huang
 */
@RunWith(Parameterized.class)
public class UpdateIT {

  @Parameters
  public static Object[] data() {
    return MongoProviderFactory.implementations();
  }

  @Rule public FongoRule fakeMongoRule = new FongoRule(false);
  @Rule public FongoRule realMongoRule = new FongoRule(true);

  private final String providerName;
  private MongoProvider provider;

  public UpdateIT(String providerName) {
    this.providerName = providerName;
  }

  private MongoCollection<BasicDBObject> collection;
  private BasicDBObject foo;
  private BasicDBObject bar;

  @Before
  public void setUp() {
    provider =
        MongoProviderFactory.newBuilder()
            .providerName(providerName)
            .fakeMongoRule(fakeMongoRule)
            .realMongoRule(realMongoRule)
            .createProvider();

    collection = provider.userCollection();
    foo = parse("{'name':'Foo', 'exams':[{'type':'C1', score:58}, {'type':'C1', score:80}]}");
    bar = parse("{'name':'Bar', 'exams':[{'type':'B1', score:83}, {'type':'B2', score:85}]}");
    collection.insertMany(List.of(foo, bar));
  }

  @After
  public void tearDown() {
    provider.close();
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
    /*
     * @impl Mongo Java Server
     * @bug com.mongodb.MongoWriteException: The positional operator did not find the match needed from the query.
     */
    assumeFalse(provider.isMongoJavaServer());

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
    /*
     * @impl Fongo
     * @bug Failed update embedded documents using $elemMatch
     * @bugLink https://github.com/fakemongo/fongo/issues/375
     */
    assumeFalse(provider.isFongo());
    /*
     * @impl Mongo Java Server
     * @bug com.mongodb.MongoWriteException: The positional operator did not find the match needed from the query.
     */
    assumeFalse(provider.isMongoJavaServer());

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
