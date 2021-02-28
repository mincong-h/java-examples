package io.mincong.mongodb.model_changes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/**
 * The new version of {@link OrderV1}. Compared to previous version, this version contains one more
 * field: "isCancelled".
 *
 * <p>Will it be backward compatible?
 */
public class OrderV2 {

  @JsonProperty("_id")
  private final String id;

  @JsonProperty("customerId")
  private final String customerId;

  @JsonProperty("amount")
  private final double amount;

  /**
   * This is a new boolean field.
   *
   * <p>For existing documents which do not contain this field, the deserialization defaults to
   * `false`.
   */
  @JsonProperty("isCanceled")
  private final boolean isCanceled;

  /**
   * This is a new operator.
   *
   * <p>For existing documents which do not contain this field, the deserialization defaults to
   * `null`. We can mark the default as "support@example.com".
   */
  @JsonProperty("operator")
  private final String operator;

  /**
   * This is a new list field.
   *
   * <p>For existing documents which do not contain this field, the deserialization defaults to
   * `null`. We should mark them as empty.
   */
  @JsonProperty("productIds")
  private final List<String> productIds;

  @JsonCreator
  public OrderV2(
      @JsonProperty("_id") String id,
      @JsonProperty("customerId") String customerId,
      @JsonProperty("amount") double amount,
      @JsonProperty("isCanceled") boolean isCanceled,
      @JsonProperty("operator") String operator,
      @JsonProperty("productIds") List<String> productIds) {
    this.id = id;
    this.customerId = customerId;
    this.amount = amount;

    // when missing value, defaults to `false`
    this.isCanceled = isCanceled;

    // when missing value, handle null explicitly
    if (operator == null) {
      this.operator = "support@example.com";
    } else {
      this.operator = operator;
    }

    // when missing value, handle null explicitly
    if (productIds == null) {
      this.productIds = List.of();
    } else {
      this.productIds = productIds;
    }
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

  public boolean getIsCanceled() {
    return isCanceled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderV2 order = (OrderV2) o;
    return Double.compare(order.amount, amount) == 0
        && Objects.equals(id, order.id)
        && Objects.equals(customerId, order.customerId)
        && Objects.equals(isCanceled, order.isCanceled)
        && Objects.equals(operator, order.operator)
        && Objects.equals(productIds, order.productIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customerId, amount, isCanceled, operator, productIds);
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
        + ", isCanceled="
        + isCanceled
        + ", operator='"
        + operator
        + '\''
        + ", productIds="
        + productIds
        + '}';
  }
}
