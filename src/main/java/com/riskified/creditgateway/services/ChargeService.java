package com.riskified.creditgateway.services;

import com.riskified.creditgateway.dtos.ChargeRequest;
import com.riskified.creditgateway.dtos.ChargeResponse;
import com.riskified.creditgateway.exceptions.BusinessException;
import com.riskified.creditgateway.factories.CreditCompanyFactory;
import com.riskified.creditgateway.interfaces.DeclinedChargesRepository;
import com.riskified.creditgateway.repositories.InMemoryDeclinedChargesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ChargeService {
    private final CreditCompanyFactory creditCompanyFactory;
    private final DeclinedChargesRepository declinedChargesRepository;

    public ChargeService(CreditCompanyFactory creditCompanyFactory,
                         InMemoryDeclinedChargesRepository declinedChargesRepository) {
        this.creditCompanyFactory = creditCompanyFactory;
        this.declinedChargesRepository = declinedChargesRepository;
    }

    public Map<String, AtomicInteger> getDeclinedStatuses(String merchantId) {
        var merchantDeclinedItems = declinedChargesRepository.getMerchantDeclinedReasons(merchantId);
        return merchantDeclinedItems;
    }

    public ChargeResponse processChargeRequest(String merchantId, ChargeRequest chargeRequest) {
        var company = creditCompanyFactory.getCreditCompany(chargeRequest.getCreditCardCompany());
        try {
            company.chargeRequest(merchantId, chargeRequest);

            return ChargeResponse
                    .builder()
                    .build();
        } catch (BusinessException businessException) {
            var reason = businessException.getMessage();
            declinedChargesRepository.addDeclinedReason(merchantId, reason);

            return ChargeResponse.builder()
                    .message(reason)
                    .build();
        }
    }
}
