package cv.dynatrace.backend.entity;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Currency
{
    private String code;
    private double exchangeRate;

    private double min;
    private double max;
    private double majorDifference;


}
