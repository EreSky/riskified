package com.riskified.creditgateway.creditcompanies.mastercard;

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
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Slf4j
@Component
public class MastercardCreditCompany extends BaseCreditCompany {

    public MastercardCreditCompany(RestTemplate restTemplate,
                                   @Value("${application.mastercard.base-url}") String baseUrl) {
        super(restTemplate);
//        restTemplate.setUriTemplateHandler(new RootUriTemplateHandler(baseUrl));
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
//        restTemplate.setUriTemplateHandler(new )
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

        HttpEntity<MastercardChargeRequest> request =
                new HttpEntity<>(MastercardChargeRequest.builder()
                        .first_name(name[0])
                        .last_name(name[1])
                        .card_number(chargeRequest.getCreditCardNumber())
                        .expiration(chargeRequest.getExpirationDate())
                        .cvv(chargeRequest.getCvv())
                        .charge_amount(chargeRequest.getAmount())
                        .build(), headers);
        try {
            var response = restTemplate.exchange("/capture_card",
                    HttpMethod.POST,
                    request,
                    MastercardChargeResult.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                var body = response.getBody();
//                if (body == null) {
//                    throw new BusinessException();
//                } else if (!"Success".equals(body.getChargeResult())) {
//                    throw new BusinessException(body.getResultReason());
//                }
            }
        } catch (HttpStatusCodeException e) {
            log.error("failed to post charge request: {}, time: {}", e.getMessage(), System.currentTimeMillis());
            throw new RetryException(e.getMessage(), e);
        }
    }

//    @Override
//    public void chargeRequest(String merchantId, ChargeRequest chargeRequest) {
////        var request = webClient.post()
////                .uri(creditCardCompanyUrl + "/capture_card", creditCardCompanyUrl)
////                .contentType(MediaType.APPLICATION_JSON)
////                .accept(MediaType.APPLICATION_JSON)
////                .header("identifier", chargeRequest.getFullName().trim().split(" ")[0])
////                .body(Mono.just(
////                        VisaChargeRequest.builder()
////                                .fullName(chargeRequest.getFullName())
////                                .number(chargeRequest.getCreditCardNumber())
////                                .expiration(chargeRequest.getExpirationDate())
////                                .cvv(chargeRequest.getCvv())
////                                .totalAmount(chargeRequest.getAmount())
////                                .build()),
////                        ChargeRequest.class);
////
////        request.exchangeToMono(response -> {
////            log.info(response.statusCode().toString());
////
////            if (response.statusCode()
////                    .equals(HttpStatus.OK)) {
////                return response.bodyToMono(MastercardChargeResult.class);
////            } else if (response.statusCode()
////                    .is4xxClientError()) {
////                return Mono.just("Error response");
////            } else {
////                return response.createException()
////                        .flatMap(Mono::error);
////            }
////        });
//    }
}
