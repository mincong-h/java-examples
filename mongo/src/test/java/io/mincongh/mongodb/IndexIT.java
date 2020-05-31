package io.mincongh.mongodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.BasicDBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoCommandException;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.mincongh.mongodb.utils.MongoProvider;
import io.mincongh.mongodb.utils.MongoProviderFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test "index" operation in MongoDB.
 *
 * @author Mincong Huang
 */
@RunWith(Parameterized.class)
public class IndexIT {

  @Parameters
  public static Object[] data() {
    return MongoProviderFactory.implementations();
  }

  @Rule public FongoRule fakeMongoRule = new FongoRule(false);
  @Rule public FongoRule realMongoRule = new FongoRule(true);

  private final String providerName;
  private MongoProvider provider;

  public IndexIT(String providerName) {
    this.providerName = providerName;
  }

  @Before
  public void setUp() {
    provider =
        MongoProviderFactory.newBuilder()
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
  public void listIndexes_empty() {
    var indexes = provider.userCollection().listIndexes();
    /*
     * By default, there is one index called "_id_" which indexes the
     * document id. This is the default index.
     *
     * https://docs.mongodb.com/manual/indexes/#default-id-index
     */
    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_");
  }

  @Test
  public void createIndexes_successDefaultName() {
    provider.userCollection().insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    provider.userCollection().insertOne(bson("{ 'name': 'bar', 'age': 30 }"));

    provider.userCollection().createIndex(Indexes.ascending("name"));
    var indexes = provider.userCollection().listIndexes();

    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  public void createIndexes_successTwoCreations() {
    provider.userCollection().insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    provider.userCollection().insertOne(bson("{ 'name': 'bar', 'age': 30 }"));

    provider.userCollection().createIndex(Indexes.ascending("name"));
    provider.userCollection().createIndex(Indexes.ascending("name"));
    // no exception

    var indexes = provider.userCollection().listIndexes();
    if (provider.isMongoJavaServer()) {
      /*
       * @impl Mongo Java Server
       * @bug One more index is created.
       *      Adding non-unique non-id index with key {} is not yet implemented
       * @see https://github.com/bwaldvogel/mongo-java-server/blob/a3dadcb4d4660fabc7dc01f4270231735aa2a0cb/core/src/main/java/de/bwaldvogel/mongo/backend/AbstractMongoDatabase.java#L754-L757
       *
       * Expecting:
       *   <["_id_", "name_1", "name_1"]>
       * to contain exactly (and in same order):
       *   <["_id_", "name_1"]>
       * but some elements were not expected:
       *   <["name_1"]>
       */
      assertThat(indexes)
          .extracting(idx -> idx.get("name"))
          .containsExactly("_id_", "name_1", "name_1");
    } else {
      assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
    }
  }

  @Test
  public void createIndexes_successDuplicateKeys() {
    provider.userCollection().insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    provider.userCollection().insertOne(bson("{ 'name': 'foo', 'age': 30 }"));

    provider.userCollection().createIndex(Indexes.ascending("name"));
    var indexes = provider.userCollection().listIndexes();

    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  public void createDocument_uniqueOptionWithoutDuplicates() {
    provider.userCollection().insertOne(bson("{ 'name': 'foo', 'age': 20 }"));

    var options = new IndexOptions().unique(true);
    assertThat(options.isUnique()).isTrue();
    provider.userCollection().createIndex(Indexes.ascending("name"), options);

    var indexes = provider.userCollection().listIndexes();
    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  public void createDocument_uniqueOptionWithDuplicates() {
    provider.userCollection().insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    provider.userCollection().insertOne(bson("{ 'name': 'foo', 'age': 30 }")); // duplicate

    var options = new IndexOptions().unique(true);
    assertThat(options.isUnique()).isTrue();
    assertThatThrownBy(
            () -> provider.userCollection().createIndex(Indexes.ascending("name"), options))
        /*
         * DuplicateKeyException: MongoDB, Mongo Java Server
         * MongoCommandException: Fongo
         */
        .matches(t -> t instanceof DuplicateKeyException || t instanceof MongoCommandException)
        .hasMessageContaining("11000");

    var indexes = provider.userCollection().listIndexes();
    assertThat(indexes)
        .extracting(idx -> idx.get("name"))
        .containsExactly("_id_")
        .doesNotContain("name_1");
  }

  private BasicDBObject bson(String json) {
    var content = json.replace("'", "\"");
    return BasicDBObject.parse(content);
  }
}
