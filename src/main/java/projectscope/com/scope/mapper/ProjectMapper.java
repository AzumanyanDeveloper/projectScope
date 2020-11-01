package projectscope.com.scope.mapper;

import org.springframework.stereotype.Service;
import projectscope.com.scope.dto.request.ProjectRequest;
import projectscope.com.scope.dto.response.ProjectResponse;
import projectscope.com.scope.entity.Log;
import projectscope.com.scope.entity.Project;
import projectscope.com.scope.repository.LogRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMapper {

    private final LogRepository logRepository;

    public ProjectMapper(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    public ProjectResponse toDto(Project project) {
        List<Log> allByProject = logRepository.findAllByProjectId(project.getId());
        Long hours = 0L;
        for (Log log : allByProject) {
            hours+= log.getHours();
        }
        return ProjectResponse.builder()
                .name(project.getName())
                .date(project.getDate())
                .deadline(project.getDeadline())
                .members(project.getMembers().stream().map(UserMapper::toDto).collect(Collectors.toList()))
                .hours(hours)
                .build();
    }



    public Project toEntity(ProjectRequest request) {
        return Project.builder()
                .name(request.getName())
                .date(LocalDate.now())
                .deadline(request.getDeadline())
                .build();

    }
}
