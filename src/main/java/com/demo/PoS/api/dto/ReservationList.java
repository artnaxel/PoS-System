package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * An object that contains a list of reservations.
 */

@Schema(name = "ReservationList", description = "An object that contains a list of reservations.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class ReservationList {

  @Valid
  private List<@Valid Reservation> product;

  public ReservationList product(List<@Valid Reservation> product) {
    this.product = product;
    return this;
  }

  public ReservationList addProductItem(Reservation productItem) {
    if (this.product == null) {
      this.product = new ArrayList<>();
    }
    this.product.add(productItem);
    return this;
  }

  /**
   * A list of Reservations.
   * @return product
  */
  @Valid 
  @Schema(name = "product", description = "A list of Reservations.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("product")
  public List<@Valid Reservation> getProduct() {
    return product;
  }

  public void setProduct(List<@Valid Reservation> product) {
    this.product = product;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationList reservationList = (ReservationList) o;
    return Objects.equals(this.product, reservationList.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationList {\n");
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
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

