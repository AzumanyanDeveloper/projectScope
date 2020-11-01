package projectscope.com.scope.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projectscope.com.scope.entity.FileEntity;
import projectscope.com.scope.entity.User;
import projectscope.com.scope.entity.UserType;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {

    private String name;
    private LocalDate date;
    private LocalDate deadline;
    private List<UserResponse> members;
    private Long hours;
}
