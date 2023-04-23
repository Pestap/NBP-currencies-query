package cv.dynatrace.backend.entity;


import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CurrencyExchangeRate {
    private String code;
    private double exchangeRate;
    private LocalDate quotationDate;
}
