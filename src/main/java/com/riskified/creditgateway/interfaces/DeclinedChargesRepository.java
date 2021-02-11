package com.riskified.creditgateway.interfaces;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//@Component
public interface DeclinedChargesRepository {
    void addDeclinedReason(String merchant, String reason);
    Map<String, AtomicInteger> getMerchantDeclinedReasons(String merchant);
}
