package projectscope.com.scope.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogRequest {

    private LocalDate date;
    private Long hours;
    private Long projectId;
}
