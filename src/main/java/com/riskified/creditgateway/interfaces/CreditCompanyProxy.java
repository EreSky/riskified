package com.riskified.creditgateway.interfaces;

import com.riskified.creditgateway.dtos.ChargeRequest;
import com.riskified.creditgateway.exceptions.BusinessException;

public interface CreditCompanyProxy {
    void chargeRequest(String merchantId, ChargeRequest chargeRequest) throws BusinessException;
}
