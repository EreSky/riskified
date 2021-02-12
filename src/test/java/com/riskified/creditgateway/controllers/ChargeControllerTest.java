package com.riskified.creditgateway.controllers;

import com.riskified.creditgateway.services.ChargeService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = ChargeController.class)
@ExtendWith(SpringExtension.class)
class ChargeControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private ChargeService chargeService;

//    @Test
//    void When_body_not_valid_return_400x() throws Exception {
//        webTestClient.post()
//                .uri("/charges")
//                .exchange()
//                .expectStatus().isBadRequest();
//    }
}
