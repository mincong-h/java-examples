package io.mincong.mongodb.model_changes;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.mincongh.mongodb.AbstractMongoIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicIT extends AbstractMongoIT {

  private MongoCollection<Order> orderCollection;
  private final Order order1 = new Order("1", "BigCorp", 100.0);

  @BeforeEach
  void setUp() {
    orderCollection = db.getCollection("orders", Order.class);
  }

  @Test
  void it_should_deserialize_existing_order() {
    // Given
    orderCollection.insertOne(order1);

    // When
    var results = orderCollection.find(Filters.eq("customer", "BigCorp"));

    // Then
    assertThat(results).containsExactly(order1);
  }
}
