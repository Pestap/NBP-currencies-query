package cv.dynatrace.backend.dto;

import cv.dynatrace.backend.entity.CurrencyMajorBuySellDifference;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate quotationDate;

    /**
     * a method that returns a function that maps an object of currency class to appropriate DTO object
     * @return - the mapping function
     */

    public static Function<CurrencyMajorBuySellDifference, GetMaxDifferenceResponse> entityToDtoMapper(){
        return currency -> GetMaxDifferenceResponse.builder()
                .code(currency.getCode())
                .majorDifference(currency.getMajorDifference())
                .quotationDate(currency.getQuotationDate())
                .build();
    }
}
