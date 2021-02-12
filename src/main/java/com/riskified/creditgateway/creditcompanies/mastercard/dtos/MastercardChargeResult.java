package com.riskified.creditgateway.creditcompanies.mastercard.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MastercardChargeResult {
    @JsonProperty("decline_reason")
    private String declineReason;
}
