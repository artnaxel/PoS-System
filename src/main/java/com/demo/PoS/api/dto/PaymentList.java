package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * Response object contains a list of Payments.
 */

@Schema(name = "PaymentList", description = "Response object contains a list of Payments.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class PaymentList {

  @Valid
  private List<@Valid Payment> payment;

  public PaymentList payment(List<@Valid Payment> payment) {
    this.payment = payment;
    return this;
  }

  public PaymentList addPaymentItem(Payment paymentItem) {
    if (this.payment == null) {
      this.payment = new ArrayList<>();
    }
    this.payment.add(paymentItem);
    return this;
  }

  /**
   * A list of Payments.
   * @return payment
  */
  @Valid 
  @Schema(name = "payment", description = "A list of Payments.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("payment")
  public List<@Valid Payment> getPayment() {
    return payment;
  }

  public void setPayment(List<@Valid Payment> payment) {
    this.payment = payment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentList paymentList = (PaymentList) o;
    return Objects.equals(this.payment, paymentList.payment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentList {\n");
    sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
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

