package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * Response object contains a list of orders.
 */

@Schema(name = "OrderList", description = "Response object contains a list of orders.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class OrderList {

  @Valid
  private List<@Valid Order> order;

  public OrderList order(List<@Valid Order> order) {
    this.order = order;
    return this;
  }

  public OrderList addOrderItem(Order orderItem) {
    if (this.order == null) {
      this.order = new ArrayList<>();
    }
    this.order.add(orderItem);
    return this;
  }

  /**
   * A list of orders.
   * @return order
  */
  @Valid 
  @Schema(name = "order", description = "A list of orders.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("order")
  public List<@Valid Order> getOrder() {
    return order;
  }

  public void setOrder(List<@Valid Order> order) {
    this.order = order;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderList orderList = (OrderList) o;
    return Objects.equals(this.order, orderList.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(order);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderList {\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

