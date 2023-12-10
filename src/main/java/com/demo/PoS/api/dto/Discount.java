package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * An object that contains information about the applied  discounts for an order.
 */

@Schema(name = "Discount", description = "An object that contains information about the applied  discounts for an order.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class Discount {

  private Long orderId;

  /**
   * Discount Type
   */
  public enum DiscountTypeEnum {
    FLAT("flat"),
    
    PERCENTAGE("percentage");

    private String value;

    DiscountTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DiscountTypeEnum fromValue(String value) {
      for (DiscountTypeEnum b : DiscountTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private DiscountTypeEnum discountType;

  private BigDecimal discountAmount;

  public Discount orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  */
  
  @Schema(name = "order_id", example = "12345", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("order_id")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Discount discountType(DiscountTypeEnum discountType) {
    this.discountType = discountType;
    return this;
  }

  /**
   * Discount Type
   * @return discountType
  */
  
  @Schema(name = "discountType", example = "percentage", description = "Discount Type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("discountType")
  public DiscountTypeEnum getDiscountType() {
    return discountType;
  }

  public void setDiscountType(DiscountTypeEnum discountType) {
    this.discountType = discountType;
  }

  public Discount discountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
    return this;
  }

  /**
   * Get discountAmount
   * @return discountAmount
  */
  @Valid 
  @Schema(name = "discountAmount", example = "10.5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("discountAmount")
  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Discount discount = (Discount) o;
    return Objects.equals(this.orderId, discount.orderId) &&
        Objects.equals(this.discountType, discount.discountType) &&
        Objects.equals(this.discountAmount, discount.discountAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, discountType, discountAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Discount {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    discountType: ").append(toIndentedString(discountType)).append("\n");
    sb.append("    discountAmount: ").append(toIndentedString(discountAmount)).append("\n");
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

