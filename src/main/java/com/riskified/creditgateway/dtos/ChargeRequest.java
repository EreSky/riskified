package com.riskified.creditgateway.dtos;

import com.riskified.creditgateway.enums.CreditCompanyType;
import com.riskified.creditgateway.validators.MonthYear;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class ChargeRequest {
    @NonNull
    @NotEmpty
    private String fullName;

    @NonNull
    @NotEmpty
    private String creditCardNumber;

    @NonNull
    private CreditCompanyType creditCardCompany;

    @NonNull
    @MonthYear
    private String expirationDate;

    @NonNull
    @NotEmpty
    private String cvv;

    @NonNull
    @Positive
    private BigDecimal amount;
}
