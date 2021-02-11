package com.riskified.creditgateway.creditcompanies.mastercard.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class MastercardChargeRequest {
    String first_name;
    String last_name;
    String card_number;
    String expiration;
    String cvv;
    BigDecimal charge_amount;
}
