package com.riskified.creditgateway.services;

import com.riskified.creditgateway.dtos.ChargeRequest;
import com.riskified.creditgateway.dtos.ChargeResponse;
import com.riskified.creditgateway.exceptions.BusinessException;
import com.riskified.creditgateway.factories.CreditCompanyFactory;
import com.riskified.creditgateway.repositories.DeclinedChargesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ChargeService {
    private final CreditCompanyFactory creditCompanyFactory;
    private final DeclinedChargesRepository declinedChargesRepository;

    public ChargeService(CreditCompanyFactory creditCompanyFactory,
                         DeclinedChargesRepository declinedChargesRepository) {
        this.creditCompanyFactory = creditCompanyFactory;
        this.declinedChargesRepository = declinedChargesRepository;
    }

    public Map<String, AtomicInteger> getDeclinedStatuses(String merchantId) {
        var merchantDeclinedItems = declinedChargesRepository.getMerchantDeclinedItems(merchantId);
        return merchantDeclinedItems;
    }

    public ResponseEntity<ChargeResponse> processChargeRequest(String merchantId, ChargeRequest chargeRequest) {
        var company = creditCompanyFactory.getCreditCompany(chargeRequest.getCreditCardCompany());
        if (company == null) { // todo: revert ResponseEntity and in case no company found throw HttpStatusCodeException
            log.error("credit card: {} doesnt exist", chargeRequest.getCreditCardCompany());
            return new ResponseEntity<>(
                    ChargeResponse
                            .builder()
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            company.chargeRequest(merchantId, chargeRequest);
            return new ResponseEntity<>(
                    ChargeResponse
                            .builder()
                            .build(),
                    HttpStatus.OK);
        } catch (BusinessException e) {
            var reason = e.getMessage();
            declinedChargesRepository.addReason(merchantId, reason);

            return new ResponseEntity<>(
                    ChargeResponse.builder()
                            .message(reason)
                            .build(),
                    HttpStatus.OK);
        }
    }
}
