package com.riskified.creditgateway.dtos;

import com.riskified.creditgateway.enums.CreditCompanyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeRequest {
    private String fullName;
    private String creditCardNumber;
    private CreditCompanyType creditCardCompany;
    private String expirationDate;
    private String cvv;
    private BigDecimal amount;
}
