package cv.dynatrace.backend;

import cv.dynatrace.backend.controller.CurrencyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private CurrencyController currencyController;

    @Test
    void contextLoads() {
        assertThat(currencyController).isNotNull();
    }
    @Test
    void testCurrency(){
        currencyController.getDateExchangeRate("eur","2023-04-21");
    }

}
