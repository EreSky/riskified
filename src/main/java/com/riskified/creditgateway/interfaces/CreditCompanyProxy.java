package com.riskified.creditgateway.interfaces;

import com.riskified.creditgateway.dtos.ChargeRequest;

public interface CreditCompanyProxy {
    void chargeRequest(String merchantId, ChargeRequest chargeRequest);
}
