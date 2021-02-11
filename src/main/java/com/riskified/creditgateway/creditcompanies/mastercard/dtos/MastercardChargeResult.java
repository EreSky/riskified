package com.riskified.creditgateway.creditcompanies.mastercard.dtos;

import lombok.Data;

@Data
public class MastercardChargeResult {
    private String decline_reason;
}
