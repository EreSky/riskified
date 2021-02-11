package com.riskified.creditgateway.controllers;

import com.riskified.creditgateway.dtos.ChargeRequest;
import com.riskified.creditgateway.services.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("charges")
@RestController
public class gatewayController {
    private final ChargeService chargeService;
    // todo: /api didnt work
    public gatewayController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PostMapping
    public void charge(@RequestBody ChargeRequest body,
                       @RequestHeader(value = "merchant-identifier") String merchantId) {
        log.info("new charge request: {} for merchant: {}", body, merchantId);
        chargeService.processChargeRequest(merchantId, body);
    }

    @GetMapping
    public void get() {
        log.info("got here");
    }
}
