package com.riskified.creditgateway.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class DeclinedChargesRepository {
    public ConcurrentMap<String, ConcurrentMap<String, AtomicInteger>> declinedCache = new ConcurrentHashMap<>();

    public void addReason(String merchant, String reason) {
        declinedCache.putIfAbsent(merchant, new ConcurrentHashMap<>());
        var reasons = declinedCache.get(merchant);
        reasons.putIfAbsent(reason, new AtomicInteger(0));
        var times = reasons.get(reason);
        times.getAndIncrement();
    }

    public ConcurrentMap<String, AtomicInteger> getMerchantDeclinedItems(String merchant) {
        return declinedCache.getOrDefault(merchant, new ConcurrentHashMap<>());
    }
}
