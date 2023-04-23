package cv.dynatrace.backend.controller;


import cv.dynatrace.backend.dto.GetExchangeRateResponse;
import cv.dynatrace.backend.dto.GetMaxAndMinRateResponse;
import cv.dynatrace.backend.dto.GetMaxDifferenceResponse;
import cv.dynatrace.backend.entity.CurrencyExchangeRate;
import cv.dynatrace.backend.entity.CurrencyMajorBuySellDifference;
import cv.dynatrace.backend.entity.CurrencyMinMaxRate;
import cv.dynatrace.backend.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
     * @param dateString - date, from which the exchange date should be provided, format: YYYY-MM-DD
     * @return - returns a response entity with the desired data
     */
    @GetMapping("{currencyCode}/{date}")
    public ResponseEntity<GetExchangeRateResponse> getDateExchangeRate(@PathVariable("currencyCode") String currencyCode,
                                                               @PathVariable("date") String dateString){

        // check if date valid, no need to check the code - if wrong NBP will return 404 not found which is handled in service
        try{
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
            Optional<CurrencyExchangeRate> result = currencyService.getExchangeRate(currencyCode, date);
            // returns a result or 404 not found
            return result
                    .map(currency -> ResponseEntity.ok(GetExchangeRateResponse.entityToDtoMapper().apply(currency)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }catch (RuntimeException ex){
            // return a 400 bad request if date is not valid
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Task 2
     * A method for returning the min and max exchange rate of a given currency from a specified number of last quotations
     * @param currencyCode - code of the currency (ISO 4217)
     * @param numberOfQuotationsString - last number of quotations from which the max and min values should be extracted (<= 255)
     * @return - response entity with desired data
     */
    @GetMapping("{currencyCode}/minAndMax/{numberOfQuotations}")
    public ResponseEntity<GetMaxAndMinRateResponse> getMaxAndMinRate(@PathVariable("currencyCode") String currencyCode,
                                                                     @PathVariable("numberOfQuotations") String numberOfQuotationsString){

        // check numberOfQuotations validity
        try{
            int numberOfQuotations = Integer.parseInt(numberOfQuotationsString);

            if(numberOfQuotations > 255 || numberOfQuotations <= 0){
                throw new NumberFormatException("Number of quotations out of range");
            }
            Optional<CurrencyMinMaxRate> result = currencyService.getMinAndMaxExchangeRates(currencyCode, numberOfQuotations);
            return result
                    .map(currency -> ResponseEntity.ok(GetMaxAndMinRateResponse.entityToDtoMapper().apply(currency)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }catch(RuntimeException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Task 3
     * A method for returning the max difference between buy and sell rates of a given currency from a specified number of last quotations
     * @param currencyCode - of the currency (ISO 4217)
     * @param numberOfQuotationsString - last number of quotations from which the max and min values should be extracted (<= 255)
     * @return - response entity with desired data
     */
    @GetMapping("{currencyCode}/maxDifference/{numberOfQuotations}")
    public ResponseEntity<GetMaxDifferenceResponse> getMaxDifference(@PathVariable("currencyCode") String currencyCode,
                                                                     @PathVariable("numberOfQuotations") String numberOfQuotationsString){

        // check numberOfQuotations validity
        try{
            int numberOfQuotations = Integer.parseInt(numberOfQuotationsString);

            if(numberOfQuotations > 255 || numberOfQuotations <= 0){
                throw new NumberFormatException("Number of quotations out of range");
            }
            Optional<CurrencyMajorBuySellDifference> result = currencyService.getMaxDifference(currencyCode, numberOfQuotations);
            return result
                    .map(currency -> ResponseEntity.ok(GetMaxDifferenceResponse.entityToDtoMapper().apply(currency)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }catch(RuntimeException ex){
            return ResponseEntity.badRequest().build();
        }
    }
}
