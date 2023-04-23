package cv.dynatrace.backend.dto;

import cv.dynatrace.backend.entity.CurrencyMinMaxRate;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;


@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetMaxAndMinRateResponse {
    private String code;
    private double min;
    private double max;
    private LocalDate minDate;
    private LocalDate maxDate;

    /**
     * a method that returns a function that maps an object of CurrencyMinMaxRate class to appropriate DTO object
     * @return - the mapping function
     */
    public static Function<CurrencyMinMaxRate, GetMaxAndMinRateResponse> entityToDtoMapper(){
        return currency -> GetMaxAndMinRateResponse.builder()
                .code(currency.getCode())
                .max(currency.getMax())
                .min(currency.getMin())
                .minDate(currency.getMinDate())
                .maxDate(currency.getMaxDate())
                .build();
    }
}
