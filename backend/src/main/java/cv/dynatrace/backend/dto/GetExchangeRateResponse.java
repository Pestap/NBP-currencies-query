package cv.dynatrace.backend.dto;

import cv.dynatrace.backend.entity.CurrencyExchangeRate;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetExchangeRateResponse {
    private String code;
    private double exchangeRate;
    private LocalDate quotationDate;

    /**
     * a method that returns a function that maps an object of currency class to appropriate DTO object
     * @return - the mapping function
     */

    public static Function<CurrencyExchangeRate, GetExchangeRateResponse> entityToDtoMapper(){
        return currency -> GetExchangeRateResponse.builder()
                .code(currency.getCode())
                .exchangeRate(currency.getExchangeRate())
                .quotationDate(currency.getQuotationDate())
                .build();
    }
}
