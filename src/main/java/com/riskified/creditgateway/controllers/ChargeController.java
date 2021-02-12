package com.riskified.creditgateway.controllers;

import com.riskified.creditgateway.dtos.ChargeRequest;
import com.riskified.creditgateway.dtos.ChargeResponse;
import com.riskified.creditgateway.services.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@RequestMapping("/api")
@RestController
public class ChargeController {
    private final ChargeService chargeService;

    // personally i think the endpoint names are not very "Resty",
    // I would leave the post as /api/charges and the get as /api/charges/status or something like that
    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PostMapping("/charges")
    public ChargeResponse charge(@RequestBody @Valid ChargeRequest body,
                                                 @RequestHeader(value = "merchant-identifier") String merchantId) {
        log.info("new charge request: {} for merchant: {}", body, merchantId);

        return chargeService.processChargeRequest(merchantId, body);
    }

    @GetMapping("/chargeStatuses")
    public Map<String, AtomicInteger> statuses(@RequestHeader(value = "merchant-identifier") String merchantId) {
        log.info("new charge status request from merchant: {}", merchantId);

        return chargeService.getDeclinedStatuses(merchantId);
    }
}
