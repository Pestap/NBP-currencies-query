package cv.dynatrace.backend.dto;

import cv.dynatrace.backend.entity.Currency;
import lombok.*;

import java.util.function.Function;


@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetMaxAndMinRateResponse {
    private String code;
    private double maxValue;
    private double minValue;

    public static Function<Currency, GetMaxAndMinRateResponse> entityToDtoMapper(){
        return currency -> GetMaxAndMinRateResponse.builder()
                .code(currency.getCode())
                .maxValue(currency.getMax())
                .minValue(currency.getMin())
                .build();
    }
}
