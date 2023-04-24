package cv.dynatrace.backend;

import cv.dynatrace.backend.controller.CurrencyController;
import cv.dynatrace.backend.dto.GetExchangeRateResponse;
import cv.dynatrace.backend.dto.GetMaxAndMinRateResponse;
import cv.dynatrace.backend.dto.GetMaxDifferenceResponse;
import cv.dynatrace.backend.entity.CurrencyMinMaxRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

@SpringBootTest
public class CurrencyControllerTests {
    @Autowired
    private CurrencyController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void testCurrencyControllerInvalidCurrency_getExchangeRate(){
        ResponseEntity<GetExchangeRateResponse> exchangeRateResponse = controller.getDateExchangeRate("asd", "2023-04-21");
        Assertions.assertEquals(exchangeRateResponse.getStatusCode().value(), 404);
    }

    @Test
    void testCurrencyControllerInvalidDate_getExchangeRate(){
        ResponseEntity<GetExchangeRateResponse> exchangeRateResponse = controller.getDateExchangeRate("EUR", "2023-13-32");
        Assertions.assertEquals(exchangeRateResponse.getStatusCode().value(), 400);
    }

    @Test
    void testCurrencyControllerValid_getExchangeRate(){
        ResponseEntity<GetExchangeRateResponse> minMaxRateResponse = controller.getDateExchangeRate("EUR", "2023-04-21");
        Assertions.assertEquals(minMaxRateResponse.getStatusCode().value(), 200);
        Assertions.assertEquals(minMaxRateResponse.getBody().getExchangeRate(), 4.6039);
    }

    @Test
    void testCurrencyControllerInvalidCurrency_getMaxAndMinRate(){
        ResponseEntity<GetMaxAndMinRateResponse> minMaxRateResponse = controller.getMaxAndMinRate("asd", "2");
        Assertions.assertEquals(minMaxRateResponse.getStatusCode().value(), 404);
    }

    @Test
    void testCurrencyControllerInvalidQuotationNumber_getMaxAndMinRate(){
        ResponseEntity<GetMaxAndMinRateResponse> minMaxRateResponse = controller.getMaxAndMinRate("EUR", "-3");
        Assertions.assertEquals(minMaxRateResponse.getStatusCode().value(), 400);
    }
    @Test
    void testCurrencyControllerInvalidQuotationNumber_getMaxAndMinRate_2(){
        ResponseEntity<GetMaxAndMinRateResponse> minMaxRateResponse = controller.getMaxAndMinRate("EUR", "sadasd");
        Assertions.assertEquals(minMaxRateResponse.getStatusCode().value(), 400);
    }

    @Test
    void testCurrencyControllerInvalidQuotationNumber_getMaxAndMinRate_3(){
        ResponseEntity<GetMaxAndMinRateResponse> minMaxRateResponse = controller.getMaxAndMinRate("EUR", "0");
        Assertions.assertEquals(minMaxRateResponse.getStatusCode().value(), 400);
    }
    @Test
    void testCurrencyControllerValid_getMaxAndMinRate(){
        ResponseEntity<GetMaxAndMinRateResponse> minMaxRateResponse = controller.getMaxAndMinRate("EUR", "1");
        Assertions.assertEquals(minMaxRateResponse.getStatusCode().value(), 200);
        Assertions.assertEquals(minMaxRateResponse.getBody().getMin(), minMaxRateResponse.getBody().getMax());
    }

    @Test
    void testCurrencyControllerInvalidCurrency_getMajorDifference(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("asd", "3");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 404);
    }

    @Test
    void testCurrencyControllerInvalidQuotationNumber_getMajorDifference(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("EUR", "-5");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 400);
    }

    @Test
    void testCurrencyControllerInvalidQuotationNumber_getMajorDifference_2(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("EUR", "0");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 400);
    }

    @Test
    void testCurrencyControllerInvalidQuotationNumber_getMajorDifference_3(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("EUR", "1000");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 400);
    }
    @Test
    void testCurrencyControllerInvalidQuotationNumber_getMajorDifference_4(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("EUR", "asdasd");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 400);
    }

    @Test
    void testCurrencyControllerValid_getMajorDifference(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("EUR", "10");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 200);
    }

    @Test
    void testCurrencyControllerValid_getMajorDifference_2(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("EUR", "1");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 200);
    }

    @Test
    void testCurrencyControllerValid_getMajorDifference_3(){
        ResponseEntity<GetMaxDifferenceResponse> maxDifferenceResponse = controller.getMaxDifference("EUR", "255");
        Assertions.assertEquals(maxDifferenceResponse.getStatusCode().value(), 200);
    }
}
