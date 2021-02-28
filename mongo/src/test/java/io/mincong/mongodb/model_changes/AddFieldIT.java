package io.mincong.mongodb.model_changes;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoCollection;
import io.mincongh.mongodb.AbstractMongoIT;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddFieldIT extends AbstractMongoIT {

  private MongoCollection<OrderV1> orderCollectionV1;
  private MongoCollection<OrderV2> orderCollectionV2;

  private final OrderV1 order1v1 = new OrderV1("1", "Customer1", 100.0);
  private final OrderV1 order2v1 = new OrderV1("2", "Customer2", 200.0);

  private final OrderV2 order1v2 =
      new OrderV2("1", "Customer1", 100.0, false, "support@example.com", List.of());
  private final OrderV2 order2v2 =
      new OrderV2("2", "Customer2", 200.0, true, "emea@example.com", List.of("A", "B", "C"));

  @BeforeEach
  void setUp() {
    orderCollectionV1 = db.getCollection("orders", OrderV1.class);
    orderCollectionV2 = db.getCollection("orders", OrderV2.class);
  }

  @Test
  void it_should_deserialize_existing_order() {
    // Given
    var insertResult1 = orderCollectionV1.insertOne(order1v1);
    assertThat(insertResult1.wasAcknowledged()).isTrue();

    var insertResult2 = orderCollectionV2.insertOne(order2v2);
    assertThat(insertResult2.wasAcknowledged()).isTrue();

    // When
    var results = orderCollectionV2.find();

    // Then
    assertThat(results).containsExactly(order1v2, order2v2);
  }

  /**
   * Without handling backward compatibility carefully, the code can raise an exception as:
   *
   * <p>"java.io.UncheckedIOException:
   * com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field
   * "isCanceled" (class io.mincong.mongodb.model_changes.OrderV1), not marked as ignorable (3 known
   * properties: "amount", "customerId", "_id"]) at [Source: (String)"{"_id": "2", "customerId":
   * "Customer2", "amount": 200.0, "isCanceled": true, "operator": "emea@example.com", "productIds":
   * ["A", "B", "C"]}"; line: 1, column: 77] (through reference chain:
   * io.mincong.mongodb.model_changes.OrderV1["isCanceled"])"
   */
  @Test
  void it_should_be_fine_when_revert_to_V1() {
    // Given
    var insertResult1 = orderCollectionV1.insertOne(order1v1);
    assertThat(insertResult1.wasAcknowledged()).isTrue();
    var insertResult2 = orderCollectionV2.insertOne(order2v2);
    assertThat(insertResult2.wasAcknowledged()).isTrue();
    var resultsV2 = orderCollectionV2.find();
    assertThat(resultsV2).containsExactly(order1v2, order2v2);

    // When
    var resultsV1 = orderCollectionV1.find();

    // Then
    assertThat(resultsV1).containsExactly(order1v1, order2v1);
  }
}
