package com.riskified.creditgateway.factories;

import com.riskified.creditgateway.interfaces.BaseCreditCompany;
import com.riskified.creditgateway.enums.CreditCompanyType;
import com.riskified.creditgateway.interfaces.CreditCompanyProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreditCompanyFactory {
    private final Map<CreditCompanyType, CreditCompanyProxy> companyMapping = new HashMap<>();

    public CreditCompanyFactory(List<BaseCreditCompany> creditCompanyList) {
        creditCompanyList.forEach(baseCreditCompany -> {
            companyMapping.put(baseCreditCompany.getCompany(), baseCreditCompany);
        });
    }

    public CreditCompanyProxy getCreditCompany(CreditCompanyType creditCompanyType){
        return companyMapping.getOrDefault(creditCompanyType, null);
    }
}
