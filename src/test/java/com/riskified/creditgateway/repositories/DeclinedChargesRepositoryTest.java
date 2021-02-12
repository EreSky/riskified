package com.riskified.creditgateway.repositories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DeclinedChargesRepositoryTest {

    private final InMemoryDeclinedChargesRepository declinedChargesRepository = new InMemoryDeclinedChargesRepository();

    @Test
    public void WHEN_ADD_TO_CACHE_IN_MULTITHREADED_ENV_WORKS() throws InterruptedException {
        var t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                declinedChargesRepository.addDeclinedReason("fox", "too poor");
            }
        });

        var t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                declinedChargesRepository.addDeclinedReason("fox", "too rich");
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        var reasons = declinedChargesRepository.getMerchantDeclinedReasons("fox");
        assertEquals(10000, reasons.get("too poor").get());
        assertEquals(5000, reasons.get("too rich").get());
    }
}
