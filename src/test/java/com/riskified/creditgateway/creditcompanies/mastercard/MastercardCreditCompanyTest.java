package com.riskified.creditgateway.creditcompanies.mastercard;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class MastercardCreditCompanyTest {

    @InjectMocks
    private MastercardCreditCompany mastercardCreditCompany;

    @Mock
    private RestTemplate restTemplate;

//    @Test()
//    public void verify() throws BusinessException {
//        var headers = new HttpHeaders();
//        headers.add("identifier", "Bibi");
//        HttpEntity<MastercardChargeRequest> request =
//                new HttpEntity<>(MastercardChargeRequest.builder()
//                        .firstName("Bibi")
//                        .lastName("Bibovic")
//                        .cardNumber("7777777777")
//                        .expiration("10-25")
//                        .cvv("879")
//                        .chargeAmount(BigDecimal.valueOf(70000))
//                        .build(), headers);
//
//        var result = new MastercardChargeResult();
//        result.setDeclineReason("bla");
//
//        Mockito.when(restTemplate.exchange("https://interview.riskxint.com/mastercard",
//                HttpMethod.POST,
//                request,
//                MastercardChargeResult.class))
//                .thenReturn(new ResponseEntity<>(result, HttpStatus.BAD_REQUEST));
//
//        ChargeRequest chargeRequest = new ChargeRequest("Bibi Bibovic",
//                "7777777777",
//                CreditCompanyType.MasterCard,
//                "10-25",
//                "879",
//                BigDecimal.valueOf(70000));
//
//        mastercardCreditCompany.chargeRequest("hamama", chargeRequest);
//    }
}
