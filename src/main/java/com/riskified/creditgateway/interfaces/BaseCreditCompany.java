package com.riskified.creditgateway.interfaces;

import com.riskified.creditgateway.enums.CreditCompanyType;
import com.riskified.creditgateway.interfaces.CreditCompanyProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public abstract class BaseCreditCompany implements CreditCompanyProxy {
    protected WebClient webClient;

    public BaseCreditCompany(WebClient webClient) {
        this.webClient = webClient;
    }

    public abstract CreditCompanyType getCompany();
}
