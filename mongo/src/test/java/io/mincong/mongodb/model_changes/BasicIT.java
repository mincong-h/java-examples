package io.mincong.mongodb.model_changes;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.mincongh.mongodb.AbstractMongoIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicIT extends AbstractMongoIT {

  private MongoCollection<OrderV1> orderCollection;
  private final OrderV1 order1 = new OrderV1("1", "BigCorp", 100.0);

  @BeforeEach
  void setUp() {
    orderCollection = db.getCollection("orders", OrderV1.class);
  }

  @Test
  void it_should_deserialize_existing_order() {
    // Given
    var result = orderCollection.insertOne(order1);
    assertThat(result.wasAcknowledged()).isTrue();

    // When
    var results = orderCollection.find(Filters.eq("customerId", "BigCorp"));

    // Then
    assertThat(results).containsExactly(order1);
  }
}
