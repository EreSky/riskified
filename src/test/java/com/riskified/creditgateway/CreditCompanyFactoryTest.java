package com.riskified.creditgateway;

import com.riskified.creditgateway.enums.CreditCompanyType;
import com.riskified.creditgateway.factories.CreditCompanyFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditCompanyFactoryTest {

    @Autowired
    private CreditCompanyFactory factory;

    @Test
    void VERIFY_ALL_COMPANY_TYPES_EXIST() {
        Stream.of(CreditCompanyType.values())
                .forEach(creditCompany -> {
                    var company = factory.getCreditCompany(creditCompany);
                    assertNotNull(company);
                });
    }
}
