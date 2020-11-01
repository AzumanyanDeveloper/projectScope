package projectscope.com.scope.mapper;

import org.springframework.stereotype.Service;
import projectscope.com.scope.dto.request.LogRequest;
import projectscope.com.scope.dto.response.LogResponse;
import projectscope.com.scope.entity.Log;

@Service
public class LogMapper {

    private final ProjectMapper projectMapper;

    public LogMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }


    public LogResponse toDto(Log log) {
        return LogResponse.builder()
                .date(log.getDate())
                .hours(log.getHours())
                .project(projectMapper.toDto(log.getProject()))
                .user(UserMapper.toDto(log.getUser()))
                .build();
    }



    public  Log toEntity(LogRequest request) {
        return Log.builder()
                .date(request.getDate())
                .hours(request.getHours())
                .build();

    }
}
