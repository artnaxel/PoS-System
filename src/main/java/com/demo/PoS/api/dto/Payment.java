package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * Response object contains information about a single Payment.
 */

@Schema(name = "Payment", description = "Response object contains information about a single Payment.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class Payment {

  private Long id;

  private Long customerId;

  private Long orderId;

  private Float amount;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate createdAt;

  /**
   * Gets or Sets paymentType
   */
  public enum PaymentTypeEnum {
    CASH("cash"),
    
    COUPON("coupon"),
    
    BANKCARD("bankCard"),
    
    LOYALTYPROGRAM("loyaltyProgram");

    private String value;

    PaymentTypeEnum(String value) {
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
    public static PaymentTypeEnum fromValue(String value) {
      for (PaymentTypeEnum b : PaymentTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private PaymentTypeEnum paymentType;

  public Payment() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Payment(Long id, Long orderId, Float amount, LocalDate createdAt, PaymentTypeEnum paymentType) {
    this.id = id;
    this.orderId = orderId;
    this.amount = amount;
    this.createdAt = createdAt;
    this.paymentType = paymentType;
  }

  public Payment id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull 
  @Schema(name = "id", example = "11111", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Payment customerId(Long customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
  */
  
  @Schema(name = "customerId", example = "22222", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerId")
  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Payment orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  */
  @NotNull 
  @Schema(name = "orderId", example = "33333", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("orderId")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Payment amount(Float amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @NotNull 
  @Schema(name = "amount", example = "10.5", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public Payment description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", example = "This is a description.  It is optional, but can be used to note anything of importance. ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Payment createdAt(LocalDate createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  @NotNull @Valid 
  @Schema(name = "createdAt", example = "Tue Sep 11 02:00:00 EET 2001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public Payment paymentType(PaymentTypeEnum paymentType) {
    this.paymentType = paymentType;
    return this;
  }

  /**
   * Get paymentType
   * @return paymentType
  */
  @NotNull 
  @Schema(name = "paymentType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("paymentType")
  public PaymentTypeEnum getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentTypeEnum paymentType) {
    this.paymentType = paymentType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payment payment = (Payment) o;
    return Objects.equals(this.id, payment.id) &&
        Objects.equals(this.customerId, payment.customerId) &&
        Objects.equals(this.orderId, payment.orderId) &&
        Objects.equals(this.amount, payment.amount) &&
        Objects.equals(this.description, payment.description) &&
        Objects.equals(this.createdAt, payment.createdAt) &&
        Objects.equals(this.paymentType, payment.paymentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customerId, orderId, amount, description, createdAt, paymentType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Payment {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    paymentType: ").append(toIndentedString(paymentType)).append("\n");
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

