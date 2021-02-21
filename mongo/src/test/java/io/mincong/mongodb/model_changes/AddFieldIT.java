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

  private final OrderV1 originalOrder1 = new OrderV1("1", "Customer1", 100.0);
  private final OrderV2 order1 =
      new OrderV2("1", "Customer1", 100.0, false, "support@example.com", List.of());
  private final OrderV2 order2 =
      new OrderV2("2", "Customer2", 200.0, true, "emea@example.com", List.of("A", "B", "C"));

  @BeforeEach
  void setUp() {
    orderCollectionV1 = db.getCollection("orders", OrderV1.class);
    orderCollectionV2 = db.getCollection("orders", OrderV2.class);
  }

  @Test
  void it_should_deserialize_existing_order() {
    // Given
    var insertResult1 = orderCollectionV1.insertOne(originalOrder1);
    assertThat(insertResult1.wasAcknowledged()).isTrue();

    var insertResult2 = orderCollectionV2.insertOne(order2);
    assertThat(insertResult2.wasAcknowledged()).isTrue();

    // When
    var results = orderCollectionV2.find();

    // Then
    assertThat(results).containsExactly(order1, order2);
  }
}
