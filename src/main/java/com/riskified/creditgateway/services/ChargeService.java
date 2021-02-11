package com.riskified.creditgateway.services;

import com.riskified.creditgateway.factories.CreditCompanyFactory;
import com.riskified.creditgateway.dtos.ChargeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChargeService {
    private final CreditCompanyFactory creditCompanyFactory;

    public ChargeService(CreditCompanyFactory creditCompanyFactory) {
        this.creditCompanyFactory = creditCompanyFactory;
    }

    public void processChargeRequest(String merchantId, ChargeRequest chargeRequest) {
        var company = creditCompanyFactory.getCreditCompany(chargeRequest.getCreditCardCompany());
        company.chargeRequest(merchantId, chargeRequest);

    }
}
