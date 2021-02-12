package com.riskified.creditgateway.creditcompanies.mastercard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riskified.creditgateway.creditcompanies.mastercard.dtos.MastercardChargeRequest;
import com.riskified.creditgateway.creditcompanies.mastercard.dtos.MastercardChargeResult;
import com.riskified.creditgateway.dtos.ChargeRequest;
import com.riskified.creditgateway.enums.CreditCompanyType;
import com.riskified.creditgateway.exceptions.BusinessException;
import com.riskified.creditgateway.interfaces.BaseCreditCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MastercardCreditCompany extends BaseCreditCompany {

    private final ObjectMapper objectMapper;

    public MastercardCreditCompany(RestTemplate restTemplate,
                                   @Value("${application.mastercard.base-url}") String baseUrl,
                                   ObjectMapper objectMapper) {
        super(restTemplate);
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
    }

    @Override
    public CreditCompanyType getCompany() {
        return CreditCompanyType.MasterCard;
    }

    @Override
    @Retryable(value = RetryException.class)
    public void chargeRequest(String merchantId, ChargeRequest chargeRequest) throws BusinessException {
        var headers = new HttpHeaders();
        String[] name = chargeRequest.getFullName().trim().split(" ");
        headers.add("identifier", name[0]);

        String formattedExpirationDate = chargeRequest.getExpirationDate().replace('/', '-');

        HttpEntity<MastercardChargeRequest> request =
                new HttpEntity<>(MastercardChargeRequest.builder()
                        .firstName(name[0])
                        .lastName(name[1])
                        .cardNumber(chargeRequest.getCreditCardNumber())
                        .expiration(formattedExpirationDate)
                        .cvv(chargeRequest.getCvv())
                        .chargeAmount(chargeRequest.getAmount())
                        .build(), headers);
        try {
            var response = restTemplate.exchange(baseUrl + "/capture_card",
                    HttpMethod.POST,
                    request,
                    MastercardChargeResult.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String responseString = e.getResponseBodyAsString();
                try {
                    MastercardChargeResult result = objectMapper.readValue(responseString,
                            MastercardChargeResult.class);

                    throw new BusinessException(result.getDeclineReason());
                } catch (JsonProcessingException parseException) {
                    log.error("failed to post charge request: {}, time: {}", e.getMessage(), System.currentTimeMillis());
                    throw new RetryException(e.getMessage(), e);
                }
            } else {
                log.error("failed to post charge request: {}, time: {}", e.getMessage(), System.currentTimeMillis());
                throw new RetryException(e.getMessage(), e);
            }
        }
    }
}
