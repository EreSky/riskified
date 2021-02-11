package com.riskified.creditgateway.creditcompanies.mastercard;

import com.riskified.creditgateway.interfaces.BaseCreditCompany;
import com.riskified.creditgateway.enums.CreditCompanyType;
import com.riskified.creditgateway.creditcompanies.mastercard.dtos.MastercardChargeResult;
import com.riskified.creditgateway.creditcompanies.visa.dtos.VisaChargeRequest;
import com.riskified.creditgateway.dtos.ChargeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MastercardCreditCompany extends BaseCreditCompany {
    @Value("${application.mastercard.base-url}")
    private String creditCardCompanyUrl;

    public MastercardCreditCompany(WebClient webClient) {
        super(webClient);
    }

    @Override
    public CreditCompanyType getCompany() {
        return CreditCompanyType.MasterCard;
    }

    @Override
    public void chargeRequest(String merchantId, ChargeRequest chargeRequest) {
        var request = webClient.post()
                .uri(creditCardCompanyUrl + "/capture_card", creditCardCompanyUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("identifier", chargeRequest.getFullName().trim().split(" ")[0])
                .body(Mono.just(
                        VisaChargeRequest.builder()
                                .fullName(chargeRequest.getFullName())
                                .number(chargeRequest.getCreditCardNumber())
                                .expiration(chargeRequest.getExpirationDate())
                                .cvv(chargeRequest.getCvv())
                                .totalAmount(chargeRequest.getAmount())
                                .build()),
                        ChargeRequest.class);

        request.exchangeToMono(response -> {
            log.info(response.statusCode().toString());

            if (response.statusCode()
                    .equals(HttpStatus.OK)) {
                return response.bodyToMono(MastercardChargeResult.class);
            } else if (response.statusCode()
                    .is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException()
                        .flatMap(Mono::error);
            }
        });
    }
}
