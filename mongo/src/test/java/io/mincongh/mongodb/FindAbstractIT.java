package io.mincongh.mongodb;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.List;
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
  }

  private BasicDBObject parse(String json) {
    var content = json.replace("'", "\"");
    return BasicDBObject.parse(content);
  }
}
