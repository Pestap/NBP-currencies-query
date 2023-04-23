package cv.dynatrace.backend.controller;


import cv.dynatrace.backend.dto.GetExchangeRateResponse;
import cv.dynatrace.backend.dto.GetMaxAndMinRateResponse;
import cv.dynatrace.backend.dto.GetMaxDifferenceResponse;
import cv.dynatrace.backend.entity.Currency;
import cv.dynatrace.backend.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    /**
     * Task 1
     * A method for returning a currencies exchange rate at a given date
     * @param currencyCode - code of the currency (ISO 4217)
     * @param date - date, from which the exchange date should be provided, format: YYYY-MM-DD
     * @return - returns a response entity with the desired data
     */
    @GetMapping("{currencyCode}/{date}")
    public ResponseEntity<GetExchangeRateResponse> getDateExchangeRate(@PathVariable("currencyCode") String currencyCode,
                                                               @PathVariable("date") LocalDate date
                                                               ){
        Optional<Currency> result = currencyService.getExchangeRate(currencyCode, date);
        return null;
    }

    /**
     * Task 2
     * A method for returning the min and max exchange rate of a given currency from a specified number of last quotations
     * @param currencyCode - code of the currency (ISO 4217)
     * @param numberOfQuotations - last number of quotations from which the max and min values should be extracted (<= 255)
     * @return - response entity with desired data
     */
    @GetMapping("{currencyCode}/minAndMax/{numberOfQuotations}")
    public ResponseEntity<GetMaxAndMinRateResponse> getMaxAndMinRate(@PathVariable("currencyCode") String currencyCode,
                                                                     @PathVariable("numberOfQuotations") int numberOfQuotations){

        return null;
    }

    /**
     * Task 3
     * A method for returning the max difference between buy and sell rates of a given currency from a specified number of last quotations
     * @param currencyCode - of the currency (ISO 4217)
     * @param numberOfQuotations - last number of quotations from which the max and min values should be extracted (<= 255)
     * @return - response entity with desired data
     */
    @GetMapping("{currencyCode}/maxDifference/{numberOfQuotations}")
    public ResponseEntity<GetMaxDifferenceResponse> getMaxDifference(@PathVariable("currencyCode") String currencyCode,
                                                                     @PathVariable("numberOfQuotations") int numberOfQuotations){
        return null;
    }
}
