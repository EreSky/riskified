package com.riskified.creditgateway.creditcompanies.visa;

import com.riskified.creditgateway.creditcompanies.visa.dtos.VisaChargeRequest;
import com.riskified.creditgateway.creditcompanies.visa.dtos.VisaChargeResult;
import com.riskified.creditgateway.dtos.ChargeRequest;
import com.riskified.creditgateway.enums.CreditCompanyType;
import com.riskified.creditgateway.enums.VisaSuccessStatus;
import com.riskified.creditgateway.exceptions.BusinessException;
import com.riskified.creditgateway.interfaces.BaseCreditCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class VisaCreditCompany extends BaseCreditCompany {

    public VisaCreditCompany(RestTemplate restTemplate,
                             @Value("${application.visa.base-url}") String baseUrl) {
        super(restTemplate);
        this.baseUrl = baseUrl;
    }

    @Override
    public CreditCompanyType getCompany() {
        return CreditCompanyType.Visa;
    }

    @Override
    @Retryable(value = RetryException.class)
    public void chargeRequest(String merchantId, ChargeRequest chargeRequest) throws BusinessException {
        var headers = new HttpHeaders();
        headers.add("identifier", chargeRequest.getFullName().trim().split(" ")[0]);

        HttpEntity<VisaChargeRequest> request =
                new HttpEntity<>(VisaChargeRequest.builder()
                        .fullName(chargeRequest.getFullName())
                        .number(chargeRequest.getCreditCardNumber())
                        .expiration(chargeRequest.getExpirationDate())
                        .cvv(chargeRequest.getCvv())
                        .totalAmount(chargeRequest.getAmount())
                        .build(), headers);
        try {
            var response = restTemplate
                    .exchange(baseUrl + "/chargeCard", HttpMethod.POST, request, VisaChargeResult.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                var body = response.getBody();
                if (body == null) {
                    throw new BusinessException();
                } else if (!VisaSuccessStatus.SUCCESS.equals(body.getChargeResult())) {
                    throw new BusinessException(body.getResultReason());
                }
            }
        } catch (HttpStatusCodeException statusCodeException) {
            log.error("failed to post charge request: {}, time: {}. going to retry", statusCodeException.getMessage(), System.currentTimeMillis());
            throw new RetryException(statusCodeException.getMessage(), statusCodeException);
        }
    }
}
