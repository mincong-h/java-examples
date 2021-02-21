package io.mincongh.mongodb;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.List;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

/**
 * Test "find" operation in MongoDB.
 *
 * @author Mincong Huang
 */
public class FindIT extends AbstractMongoIT {

  private MongoCollection<Document> userCollection;

  @Before
  public void setUp() {
    super.setUp();

    db.createCollection("users");
    userCollection = db.getCollection("users");
  }

  @Test
  public void find_eq() {
    var foo = parse("{'name':'Foo', 'age':20}");
    var bar = parse("{'name':'Bar', 'age':20}");
    userCollection.insertMany(List.of(foo, bar));

    var results = userCollection.find(Filters.eq("name", "Foo"));
    assertThat(results).containsExactly(foo);
  }

  @Test
  public void find_elemMatch() {
    var foo = parse("{'name':'Foo', 'exams':[{'type':'C1', score:58}, {'type':'C1', score:80}]}");
    var bar = parse("{'name':'Bar', 'exams':[{'type':'B1', score:83}, {'type':'B2', score:85}]}");
    userCollection.insertMany(List.of(foo, bar));

    var f = Filters.and(Filters.eq("type", "C1"), Filters.lt("score", 60));
    var results = userCollection.find(Filters.elemMatch("exams", f));
    assertThat(results).containsExactly(foo);

    var results2 = userCollection.find(Filters.elemMatch("exams", Filters.gt("score", 60)));
    assertThat(results2).containsExactly(foo, bar);
  }

  private Document parse(String json) {
    var content = json.replace("'", "\"");
    return Document.parse(content);
  }
}
