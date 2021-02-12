package com.riskified.creditgateway.creditcompanies.mastercard.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class MastercardChargeRequest {
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("card_number")
    String cardNumber;

    String expiration;
    String cvv;

    @JsonProperty("charge_amount")
    BigDecimal chargeAmount;
}
