package projectscope.com.scope.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogResponse {

    private LocalDate date;
    private Long hours;
    private ProjectResponse project;
    private UserResponse user;
}
