package com.demo.PoS.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * An object that contains information about a  reservation for a product or service. 
 */

@Schema(name = "Reservation", description = "An object that contains information about a  reservation for a product or service. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-07T23:27:22.493057110+02:00[Europe/Vilnius]")
public class Reservation {

  private Long reservationId;

  private Long orderId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startTime;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endTime;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate creationTime;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate lastUpdateTime;

  public Reservation reservationId(Long reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  /**
   * Get reservationId
   * @return reservationId
  */
  
  @Schema(name = "reservationId", example = "12345", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reservationId")
  public Long getReservationId() {
    return reservationId;
  }

  public void setReservationId(Long reservationId) {
    this.reservationId = reservationId;
  }

  public Reservation orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  */
  
  @Schema(name = "orderId", example = "12345", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orderId")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Reservation startTime(LocalDate startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Get startTime
   * @return startTime
  */
  @Valid 
  @Schema(name = "startTime", example = "Fri Aug 25 03:00:00 EEST 2023", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startTime")
  public LocalDate getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDate startTime) {
    this.startTime = startTime;
  }

  public Reservation endTime(LocalDate endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Get endTime
   * @return endTime
  */
  @Valid 
  @Schema(name = "endTime", example = "Fri Aug 25 03:00:00 EEST 2023", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endTime")
  public LocalDate getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDate endTime) {
    this.endTime = endTime;
  }

  public Reservation creationTime(LocalDate creationTime) {
    this.creationTime = creationTime;
    return this;
  }

  /**
   * Get creationTime
   * @return creationTime
  */
  @Valid 
  @Schema(name = "creationTime", example = "Fri Aug 25 03:00:00 EEST 2023", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("creationTime")
  public LocalDate getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDate creationTime) {
    this.creationTime = creationTime;
  }

  public Reservation lastUpdateTime(LocalDate lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
    return this;
  }

  /**
   * Get lastUpdateTime
   * @return lastUpdateTime
  */
  @Valid 
  @Schema(name = "lastUpdateTime", example = "Fri Aug 25 03:00:00 EEST 2023", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastUpdateTime")
  public LocalDate getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(LocalDate lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reservation reservation = (Reservation) o;
    return Objects.equals(this.reservationId, reservation.reservationId) &&
        Objects.equals(this.orderId, reservation.orderId) &&
        Objects.equals(this.startTime, reservation.startTime) &&
        Objects.equals(this.endTime, reservation.endTime) &&
        Objects.equals(this.creationTime, reservation.creationTime) &&
        Objects.equals(this.lastUpdateTime, reservation.lastUpdateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationId, orderId, startTime, endTime, creationTime, lastUpdateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Reservation {\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    creationTime: ").append(toIndentedString(creationTime)).append("\n");
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
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

