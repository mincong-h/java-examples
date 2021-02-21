package io.mincongh.mongodb;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoCollection;
import java.util.Arrays;
import java.util.Collections;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.Test;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class MongoViewIT extends AbstractMongoIT {

  @Test
  public void createView() {
    db.getCollection("users")
        .insertMany(
            Arrays.asList(
                Document.parse("{\"firstName\":\"Robb\",\"lastName\":\"Stark\"}"),
                Document.parse("{\"firstName\":\"Sansa\",\"lastName\":\"Stark\"}"),
                Document.parse("{\"firstName\":\"Arya\",\"lastName\":\"Stark\"}"),
                Document.parse("{\"firstName\":\"Bran\",\"lastName\":\"Stark\"}"),
                Document.parse("{\"firstName\":\"Rickon\",\"lastName\":\"Stark\"}"),
                Document.parse("{\"firstName\":\"Cersei\",\"lastName\":\"Lannister\"}")));

    /*
     * Views exhibit the following behavior:
     *
     * - Read Only
     *   - Views are read-only; write operations on views will error.
     * - Index Use and Sort Operations
     *   - Views use the indexes of the underlying collection.
     *   - As the indexes are on the underlying collection, you cannot create,
     *     drop or re-build indexes on the view directly nor get a list of
     *     indexes on the view.
     *   - You cannot specify a natural sort on a view.
     * - Projection Restrictions
     *   - find() operations on views do not support the following project
     *     operators: $, $elemMatch, $slice, $meta
     * - Immutable Name
     *   - You cannot rename views.
     * - View Creation
     *   - Views are computed on demand during read operations, and MongoDB
     *     executes read operations on views as part of the underlying
     *     aggregation
     * - Shared View
     * - Views and Collation
     * - Public View Definition
     *
     * See: https://docs.mongodb.com/manual/core/views/
     */
    db.createView("view", "users", Collections.<BsonDocument>emptyList());

    MongoCollection<Document> users = db.getCollection("users");
    assertThat(users.countDocuments()).isEqualTo(6);
  }
}
