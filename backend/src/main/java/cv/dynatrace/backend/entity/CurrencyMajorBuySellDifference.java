package cv.dynatrace.backend.entity;


import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CurrencyMajorBuySellDifference {
    private String code;
    private double majorDifference;
    private LocalDate quotationDate;
}
