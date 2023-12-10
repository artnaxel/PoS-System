package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * An object that contains information about an order.
 */

@Schema(name = "Order", description = "An object that contains information about an order.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class Order {

  private Long id;

  private Long customerId;

  private Long employeeId;

  @Valid
  private List<Integer> itemIdList;

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

  private BigDecimal tippingAmount;

  /**
   * Order status
   */
  public enum StatusEnum {
    CREATED("created"),
    
    PAID("paid"),
    
    COMPLETED("completed"),
    
    CANCELLED("cancelled"),
    
    REFUNDED("refunded");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  public Order id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Order customerId(Long customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
  */
  
  @Schema(name = "customerId", example = "1987", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerId")
  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Order employeeId(Long employeeId) {
    this.employeeId = employeeId;
    return this;
  }

  /**
   * Get employeeId
   * @return employeeId
  */
  
  @Schema(name = "employeeId", example = "7662", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeeId")
  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public Order itemIdList(List<Integer> itemIdList) {
    this.itemIdList = itemIdList;
    return this;
  }

  public Order addItemIdListItem(Integer itemIdListItem) {
    if (this.itemIdList == null) {
      this.itemIdList = new ArrayList<>();
    }
    this.itemIdList.add(itemIdListItem);
    return this;
  }

  /**
   * Get itemIdList
   * @return itemIdList
  */
  
  @Schema(name = "itemIdList", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemIdList")
  public List<Integer> getItemIdList() {
    return itemIdList;
  }

  public void setItemIdList(List<Integer> itemIdList) {
    this.itemIdList = itemIdList;
  }

  public Order discountType(DiscountTypeEnum discountType) {
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

  public Order discountAmount(BigDecimal discountAmount) {
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

  public Order tippingAmount(BigDecimal tippingAmount) {
    this.tippingAmount = tippingAmount;
    return this;
  }

  /**
   * Get tippingAmount
   * @return tippingAmount
  */
  @Valid 
  @Schema(name = "tippingAmount", example = "5.23", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tippingAmount")
  public BigDecimal getTippingAmount() {
    return tippingAmount;
  }

  public void setTippingAmount(BigDecimal tippingAmount) {
    this.tippingAmount = tippingAmount;
  }

  public Order status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Order status
   * @return status
  */
  
  @Schema(name = "status", example = "created", description = "Order status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.id, order.id) &&
        Objects.equals(this.customerId, order.customerId) &&
        Objects.equals(this.employeeId, order.employeeId) &&
        Objects.equals(this.itemIdList, order.itemIdList) &&
        Objects.equals(this.discountType, order.discountType) &&
        Objects.equals(this.discountAmount, order.discountAmount) &&
        Objects.equals(this.tippingAmount, order.tippingAmount) &&
        Objects.equals(this.status, order.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customerId, employeeId, itemIdList, discountType, discountAmount, tippingAmount, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    employeeId: ").append(toIndentedString(employeeId)).append("\n");
    sb.append("    itemIdList: ").append(toIndentedString(itemIdList)).append("\n");
    sb.append("    discountType: ").append(toIndentedString(discountType)).append("\n");
    sb.append("    discountAmount: ").append(toIndentedString(discountAmount)).append("\n");
    sb.append("    tippingAmount: ").append(toIndentedString(tippingAmount)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

