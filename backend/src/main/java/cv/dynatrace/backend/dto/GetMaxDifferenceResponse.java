package cv.dynatrace.backend.dto;

import cv.dynatrace.backend.entity.Currency;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetMaxDifferenceResponse
{
    private String code;
    private double majorDifference;

    public static Function<Currency, GetMaxDifferenceResponse> entityToDtoMapper(){
        return currency -> GetMaxDifferenceResponse.builder()
                .code(currency.getCode())
                .majorDifference(currency.getMajorDifference())
                .build();
    }
}
