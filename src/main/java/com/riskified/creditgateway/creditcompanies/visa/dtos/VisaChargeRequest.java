package com.riskified.creditgateway.creditcompanies.visa.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class VisaChargeRequest {
    String fullName;
    String number;
    String expiration;
    String cvv;
    BigDecimal totalAmount;
}
