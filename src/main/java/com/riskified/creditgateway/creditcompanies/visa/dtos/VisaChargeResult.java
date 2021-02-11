package com.riskified.creditgateway.creditcompanies.visa.dtos;

import lombok.Data;

@Data
public class VisaChargeResult {
    private String chargeResult;
    private String resultReason;
}
