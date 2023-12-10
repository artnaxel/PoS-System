package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * An object that contains a list of products.
 */

@Schema(name = "ProductList", description = "An object that contains a list of products.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class ProductList {

  @Valid
  private List<@Valid Product> product;

  public ProductList product(List<@Valid Product> product) {
    this.product = product;
    return this;
  }

  public ProductList addProductItem(Product productItem) {
    if (this.product == null) {
      this.product = new ArrayList<>();
    }
    this.product.add(productItem);
    return this;
  }

  /**
   * A list of products.
   * @return product
  */
  @Valid 
  @Schema(name = "product", description = "A list of products.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("product")
  public List<@Valid Product> getProduct() {
    return product;
  }

  public void setProduct(List<@Valid Product> product) {
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
    ProductList productList = (ProductList) o;
    return Objects.equals(this.product, productList.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductList {\n");
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

