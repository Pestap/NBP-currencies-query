package cv.dynatrace.backend;

import cv.dynatrace.backend.controller.CurrencyController;
import cv.dynatrace.backend.entity.CurrencyExchangeRate;
import cv.dynatrace.backend.entity.CurrencyMajorBuySellDifference;
import cv.dynatrace.backend.entity.CurrencyMinMaxRate;
import cv.dynatrace.backend.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private CurrencyController currencyController;
    @Autowired
    private CurrencyService currencyService;

    @Test
    void contextLoads() {
        assertThat(currencyController).isNotNull();
    }

    // exchange rates
    @Test
    void testCurrencyServiceInvalidCurrency_getExchangeRate(){
        Optional<CurrencyExchangeRate> exchangeRate = currencyService.getExchangeRate("ASD", LocalDate.of(2023, 04, 21));
        assertThat(exchangeRate.isEmpty()).isTrue();
    }
    @Test
    void testCurrencyServiceInvalidDate(){
        Optional<CurrencyExchangeRate> exchangeRate = currencyService.getExchangeRate("ASD", LocalDate.of(2023, 04, 23));
        assertThat(exchangeRate.isEmpty()).isTrue();
    }

    @Test
    void testCurrencyServiceValidCurrency_getExchangeRate(){
        Optional<CurrencyExchangeRate> exchangeRate = currencyService.getExchangeRate("EUR", LocalDate.of(2023, 04, 21));
        CurrencyExchangeRate exchangeRateObject = exchangeRate.get();
        Assertions.assertEquals(exchangeRateObject.getExchangeRate(), 4.6039);
    }

    @Test
    void testCurrencyServiceValidCurrency_getExchangeRate_2(){
        Optional<CurrencyExchangeRate> exchangeRate = currencyService.getExchangeRate("EUR", LocalDate.of(2022, 04, 21));
        CurrencyExchangeRate exchangeRateObject = exchangeRate.get();
        Assertions.assertEquals(exchangeRateObject.getExchangeRate(), 4.6523);
    }

    // Min and Max exchange rates
    @Test
    void testCurrencyServiceInvalidCurrency_getMinAndMaxExchangeRates(){
        Optional<CurrencyMinMaxRate> exchangeRate = currencyService.getMinAndMaxExchangeRates("ASD",10);
        assertThat(exchangeRate.isEmpty()).isTrue();
    }

    @Test
    void testCurrencyServiceInvalidQuotationNumber_getMinAndMaxExchangeRates(){
        Optional<CurrencyMinMaxRate> exchangeRate = currencyService.getMinAndMaxExchangeRates("EUR",-2);
        assertThat(exchangeRate.isEmpty()).isTrue();
    }
    @Test
    void testCurrencyServiceInvalidQuotationNumber_getMinAndMaxExchangeRates_2(){
        Optional<CurrencyMinMaxRate> exchangeRate = currencyService.getMinAndMaxExchangeRates("EUR",123123);
        assertThat(exchangeRate.isEmpty()).isTrue();
    }

    @Test
    void testCurrencyServiceValid1_getMinAndMaxExchangeRates(){
        Optional<CurrencyMinMaxRate> exchangeRate = currencyService.getMinAndMaxExchangeRates("EUR",1);
        CurrencyMinMaxRate exchangeRateObject = exchangeRate.get();

        Assertions.assertEquals(exchangeRateObject.getMin(), exchangeRateObject.getMax());
    }

    @Test
    void testCurrencyServiceValid_getMinAndMaxExchangeRates(){
        Optional<CurrencyMinMaxRate> exchangeRate = currencyService.getMinAndMaxExchangeRates("EUR",5);
        CurrencyMinMaxRate exchangeRateObject = exchangeRate.get();

        Assertions.assertEquals(exchangeRateObject.getMin(), 4.6039);
        Assertions.assertEquals(exchangeRateObject.getMax(), 4.6341);
    }

    // max difference
    @Test
    void testCurrencyServiceInvalidCurrency_getMaxDiffference(){
        Optional<CurrencyMajorBuySellDifference> majorBuySellDifference = currencyService.getMaxDifference("aSd", 10);
        assertThat(majorBuySellDifference.isEmpty()).isTrue();
    }

    @Test
    void testCurrencyServiceInvalidQuotationNumber_getMaxDiffference(){
        Optional<CurrencyMajorBuySellDifference> majorBuySellDifference = currencyService.getMaxDifference("EUR", -3);
        assertThat(majorBuySellDifference.isEmpty()).isTrue();
    }

    @Test
    void testCurrencyServiceInvalidQuotationNumber_getMaxDiffference_2(){
        Optional<CurrencyMajorBuySellDifference> majorBuySellDifference = currencyService.getMaxDifference("EUR", 10000);
        assertThat(majorBuySellDifference.isEmpty()).isTrue();
    }






}
