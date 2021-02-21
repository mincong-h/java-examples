package io.mincong.mongodb.model_changes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Order {
  @JsonProperty("id")
  private final String id;

  @JsonProperty("customerId")
  private final String customer;

  @JsonProperty("amount")
  private final double amount;

  @JsonCreator
  public Order(
      @JsonProperty("id") String id,
      @JsonProperty("customerId") String customer,
      @JsonProperty("amount") double amount) {
    this.id = id;
    this.customer = customer;
    this.amount = amount;
  }

  public String getId() {
    return id;
  }

  public String getCustomer() {
    return customer;
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
        && Objects.equals(customer, order.customer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customer, amount);
  }

  @Override
  public String toString() {
    return "Order{"
        + "id='"
        + id
        + '\''
        + ", customer='"
        + customer
        + '\''
        + ", amount="
        + amount
        + '}';
  }
}
