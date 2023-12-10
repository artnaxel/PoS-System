package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * A receipt object
 */

@Schema(name = "Receipt", description = "A receipt object")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class Receipt {

  private Long orderId;

  private String receiptString;

  public Receipt() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Receipt(Long orderId) {
    this.orderId = orderId;
  }

  public Receipt orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  */
  @NotNull 
  @Schema(name = "orderId", example = "22222", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("orderId")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Receipt receiptString(String receiptString) {
    this.receiptString = receiptString;
    return this;
  }

  /**
   * Get receiptString
   * @return receiptString
  */
  
  @Schema(name = "receiptString", example = "This order costs 12$ \\nPaid by credit card.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("receiptString")
  public String getReceiptString() {
    return receiptString;
  }

  public void setReceiptString(String receiptString) {
    this.receiptString = receiptString;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Receipt receipt = (Receipt) o;
    return Objects.equals(this.orderId, receipt.orderId) &&
        Objects.equals(this.receiptString, receipt.receiptString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, receiptString);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Receipt {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    receiptString: ").append(toIndentedString(receiptString)).append("\n");
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

