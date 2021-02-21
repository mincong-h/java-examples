package io.mincong.mongodb.model_changes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Order {
  @JsonProperty("_id")
  private final String id;

  @JsonProperty("customerId")
  private final String customerId;

  @JsonProperty("amount")
  private final double amount;

  @JsonCreator
  public Order(
      @JsonProperty("_id") String id,
      @JsonProperty("customerId") String customerId,
      @JsonProperty("amount") double amount) {
    this.id = id;
    this.customerId = customerId;
    this.amount = amount;
  }

  public String getId() {
    return id;
  }

  public String getCustomerId() {
    return customerId;
  }

  public double getAmount() {
    return amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Double.compare(order.amount, amount) == 0
        && Objects.equals(id, order.id)
        && Objects.equals(customerId, order.customerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customerId, amount);
  }

  @Override
  public String toString() {
    return "Order{"
        + "id='"
        + id
        + '\''
        + ", customerId='"
        + customerId
        + '\''
        + ", amount="
        + amount
        + '}';
  }
}
