package projectscope.com.scope.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectscope.com.scope.dto.request.LogRequest;
import projectscope.com.scope.dto.response.LogResponse;
import projectscope.com.scope.dto.response.ProjectResponse;
import projectscope.com.scope.entity.Log;
import projectscope.com.scope.entity.Project;
import projectscope.com.scope.entity.User;
import projectscope.com.scope.mapper.LogMapper;
import projectscope.com.scope.repository.LogRepository;
import projectscope.com.scope.security.service.SecurityService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogService {
    private final SecurityService securityService;
    private final LogRepository logRepository;
    private final ProjectService projectService;
    private final LogMapper logMapper;

    public LogService(SecurityService securityService, LogRepository logRepository, ProjectService projectService, LogMapper logMapper) {
        this.securityService = securityService;
        this.logRepository = logRepository;
        this.projectService = projectService;
        this.logMapper = logMapper;
    }


    public LogResponse createLog(LogRequest request) {
        User user = securityService.fetchCurrentUser();
        Project project = projectService.getById(request.getProjectId());
        Log log = logMapper.toEntity(request);
        log.setUser(user);
        log.setProject(project);
        Log actualLog = logRepository.save(log);
        return logMapper.toDto(actualLog);

    }

    public Page<LogResponse> getMyLogs(Pageable pageable) {
        User user = securityService.fetchCurrentUser();
        Page<Log> logs = logRepository.findAllByUser(user,pageable);
        List<LogResponse> logList = logs.stream().map(logMapper::toDto).collect(Collectors.toList());
        return new PageImpl(logList);
    }

    public ResponseEntity<String> deleteLogs(Long logId) {
        User user = securityService.fetchCurrentUser();
        Log log = logRepository.findById(logId).orElseThrow(IllegalArgumentException::new);
        if (log.getUser().getId().equals(user.getId())){
            log.setProject(null);
            logRepository.save(log);
            logRepository.deleteById(log.getId());
            return ResponseEntity.ok().body("Log successfully deleted");
        }
        return ResponseEntity.badRequest().body("You can't delete this log");
    }

}
