package projectscope.com.scope.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projectscope.com.scope.entity.FileEntity;
import projectscope.com.scope.entity.User;
import projectscope.com.scope.entity.UserType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {

    private String name;
    private List<Long> members;
    private LocalDate deadline;

}
