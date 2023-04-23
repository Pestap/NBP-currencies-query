package cv.dynatrace.backend.entity;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CurrencyMinMaxRate {
    private String code;
    private double min;
    private LocalDate minDate;
    private double max;
    private LocalDate maxDate;
}
