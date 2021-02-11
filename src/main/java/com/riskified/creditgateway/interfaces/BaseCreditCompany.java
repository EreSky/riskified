package com.riskified.creditgateway.interfaces;

import com.riskified.creditgateway.enums.CreditCompanyType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public abstract class BaseCreditCompany implements CreditCompanyProxy {
    protected RestTemplate restTemplate;

    public BaseCreditCompany(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public abstract CreditCompanyType getCompany();
}
