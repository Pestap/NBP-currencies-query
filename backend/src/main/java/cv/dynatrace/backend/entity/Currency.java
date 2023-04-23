package cv.dynatrace.backend.entity;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Currency
{
    private String code;
    private double exchangeRate;

    public Currency(String code, double exchangeRate){
        this.code = code;
        this.exchangeRate = exchangeRate;
    }
}
