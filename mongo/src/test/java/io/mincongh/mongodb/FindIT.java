package io.mincongh.mongodb;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import io.mincongh.mongodb.utils.*;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test "find" operation in MongoDB.
 *
 * @author Mincong Huang
 */
@RunWith(Parameterized.class)
public class FindIT {

  @Parameters
  public static Object[] data() {
    return MongoProviderFactory.implementations();
  }

  @Rule public FongoRule fakeMongoRule = new FongoRule(false);
  @Rule public FongoRule realMongoRule = new FongoRule(true);

  private final String providerName;
  private MongoProvider provider;

  public FindIT(String providerName) {
    this.providerName = providerName;
  }

  @Before
  public void setUp() {
    provider = MongoProviderFactory.newBuilder()
        .providerName(providerName)
        .fakeMongoRule(fakeMongoRule)
        .realMongoRule(realMongoRule)
        .createProvider();
  }

  @After
  public void tearDown() {
    provider.close();
  }

  @Test
  public void find_eq() {
    var foo = parse("{'name':'Foo', 'age':20}");
    var bar = parse("{'name':'Bar', 'age':20}");
    provider.userCollection().insertMany(List.of(foo, bar));

    var results = provider.userCollection().find(Filters.eq("name", "Foo"));
    assertThat(results).containsExactly(foo);
  }

  @Test
  public void find_elemMatch() {
    var foo = parse("{'name':'Foo', 'exams':[{'type':'C1', score:58}, {'type':'C1', score:80}]}");
    var bar = parse("{'name':'Bar', 'exams':[{'type':'B1', score:83}, {'type':'B2', score:85}]}");
    provider.userCollection().insertMany(List.of(foo, bar));

    var f = Filters.and(Filters.eq("type", "C1"), Filters.lt("score", 60));
    var results = provider.userCollection().find(Filters.elemMatch("exams", f));
    assertThat(results).containsExactly(foo);

    var results2 =
        provider.userCollection().find(Filters.elemMatch("exams", Filters.gt("score", 60)));
    assertThat(results2).containsExactly(foo, bar);
  }

  private BasicDBObject parse(String json) {
    var content = json.replace("'", "\"");
    return BasicDBObject.parse(content);
  }

}
