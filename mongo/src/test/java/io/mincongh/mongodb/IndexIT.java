package io.mincongh.mongodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test "index" operation in MongoDB.
 *
 * @author Mincong Huang
 */
class IndexIT extends AbstractMongoIT {

  private MongoCollection<Document> userCollection;

  @BeforeEach
  void setUp() {
    db.createCollection("users");
    userCollection = db.getCollection("users");
  }

  @Test
  void listIndexes_empty() {
    var indexes = userCollection.listIndexes();
    /*
     * By default, there is one index called "_id_" which indexes the
     * document id. This is the default index.
     *
     * https://docs.mongodb.com/manual/indexes/#default-id-index
     */
    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_");
  }

  @Test
  void createIndexes_successDefaultName() {
    userCollection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    userCollection.insertOne(bson("{ 'name': 'bar', 'age': 30 }"));

    userCollection.createIndex(Indexes.ascending("name"));
    var indexes = userCollection.listIndexes();

    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  void createIndexes_successTwoCreations() {
    userCollection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    userCollection.insertOne(bson("{ 'name': 'bar', 'age': 30 }"));

    userCollection.createIndex(Indexes.ascending("name"));
    userCollection.createIndex(Indexes.ascending("name"));
    // no exception

    var indexes = userCollection.listIndexes();
    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  void createIndexes_successDuplicateKeys() {
    userCollection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    userCollection.insertOne(bson("{ 'name': 'foo', 'age': 30 }"));

    userCollection.createIndex(Indexes.ascending("name"));
    var indexes = userCollection.listIndexes();

    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  void createDocument_uniqueOptionWithoutDuplicates() {
    userCollection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));

    var options = new IndexOptions().unique(true);
    assertThat(options.isUnique()).isTrue();
    userCollection.createIndex(Indexes.ascending("name"), options);

    var indexes = userCollection.listIndexes();
    assertThat(indexes).extracting(idx -> idx.get("name")).containsExactly("_id_", "name_1");
  }

  @Test
  void createDocument_uniqueOptionWithDuplicates() {
    userCollection.insertOne(bson("{ 'name': 'foo', 'age': 20 }"));
    userCollection.insertOne(bson("{ 'name': 'foo', 'age': 30 }")); // duplicate

    var options = new IndexOptions().unique(true);
    assertThat(options.isUnique()).isTrue();
    assertThatThrownBy(() -> userCollection.createIndex(Indexes.ascending("name"), options))
        /*
         * DuplicateKeyException: MongoDB, Mongo Java Server
         * MongoCommandException: Fongo
         */
        .matches(t -> t instanceof DuplicateKeyException || t instanceof MongoCommandException)
        .hasMessageContaining("11000");

    var indexes = userCollection.listIndexes();
    assertThat(indexes)
        .extracting(idx -> idx.get("name"))
        .containsExactly("_id_")
        .doesNotContain("name_1");
  }

  private Document bson(String json) {
    var content = json.replace("'", "\"");
    return Document.parse(content);
  }
}
