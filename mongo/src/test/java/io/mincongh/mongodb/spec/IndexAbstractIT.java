package io.mincongh.mongodb.spec;

import com.mongodb.BasicDBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test "index" operation in MongoDB.
 *
 * @author Mincong Huang
 */
public abstract class IndexAbstractIT {

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
  public void listIndexes_empty() {
    var indexes = collection.listIndexes();
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
    collection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    collection.insertOne(bson("{ 'name': 'bar', 'age': 30 }"));

    collection.createIndex(Indexes.ascending("name"));
    var indexes = collection.listIndexes();

    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  public void createIndexes_successTwoCreations() {
    collection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    collection.insertOne(bson("{ 'name': 'bar', 'age': 30 }"));

    collection.createIndex(Indexes.ascending("name"));
    collection.createIndex(Indexes.ascending("name"));
    // no exception

    var indexes = collection.listIndexes();
    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  public void createIndexes_successDuplicateKeys() {
    collection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    collection.insertOne(bson("{ 'name': 'foo', 'age': 30 }"));

    collection.createIndex(Indexes.ascending("name"));
    var indexes = collection.listIndexes();

    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  public void createDocument_uniqueOptionWithoutDuplicates() {
    collection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));

    var options = new IndexOptions().unique(true);
    assertThat(options.isUnique()).isTrue();
    collection.createIndex(Indexes.ascending("name"), options);

    var indexes = collection.listIndexes();
    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  public void createDocument_uniqueOptionWithDuplicates() {
    collection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    collection.insertOne(bson("{ 'name': 'foo', 'age': 30 }")); // duplicate

    var options = new IndexOptions().unique(true);
    assertThat(options.isUnique()).isTrue();
    assertThatThrownBy(() -> collection.createIndex(Indexes.ascending("name"), options))
        /*
         * DuplicateKeyException: MongoDB, Mongo Java Server
         * MongoCommandException: Fongo
         */
        .matches(t -> t instanceof DuplicateKeyException || t instanceof MongoCommandException)
        .hasMessageContaining("11000");

    var indexes = collection.listIndexes();
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
