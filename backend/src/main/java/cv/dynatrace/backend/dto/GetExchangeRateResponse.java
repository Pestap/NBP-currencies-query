package cv.dynatrace.backend.dto;

import cv.dynatrace.backend.entity.Currency;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetExchangeRateResponse {
    private String code;
    private double exchangeRate;

    public static Function<Currency, GetExchangeRateResponse> entityToDtoMapper(){
        return currency -> GetExchangeRateResponse.builder()
                .code(currency.getCode())
                .exchangeRate(currency.getExchangeRate())
                .build();
    }
}
