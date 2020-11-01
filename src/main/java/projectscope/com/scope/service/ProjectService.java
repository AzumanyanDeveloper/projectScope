package projectscope.com.scope.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectscope.com.scope.dto.request.ProjectRequest;
import projectscope.com.scope.dto.response.ProjectResponse;
import projectscope.com.scope.entity.Log;
import projectscope.com.scope.entity.Project;
import projectscope.com.scope.entity.User;
import projectscope.com.scope.exception.ProjectNotFoundException;
import projectscope.com.scope.mapper.ProjectMapper;
import projectscope.com.scope.repository.LogRepository;
import projectscope.com.scope.repository.ProjectRepository;
import projectscope.com.scope.repository.UserRepository;
import projectscope.com.scope.security.service.SecurityService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final SecurityService securityService;
    private final ProjectMapper projectMapper;
    private final LogRepository logRepository;

    public ProjectService(ProjectRepository projectRepository, SecurityService securityService, UserRepository userRepository, UserService userService, SecurityService securityService1, ProjectMapper projectMapper, LogRepository logRepository) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.securityService = securityService1;
        this.projectMapper = projectMapper;
        this.logRepository = logRepository;
    }

    public Project getById(Long projectId){
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

    }


    public ProjectResponse createProject(ProjectRequest request) {
        User user = securityService.fetchCurrentUser();
        Project project = projectMapper.toEntity(request);
        project.setMembers(request.getMembers().stream().map(userService::getById).collect(Collectors.toList()));
        project.setUser(user);
        Project actualProject = projectRepository.save(project);
        return projectMapper.toDto(actualProject);

    }

    public Page<ProjectResponse> getMyProjects(Pageable pageable) {
        User user = securityService.fetchCurrentUser();
        Page<Project> projects = projectRepository.findAllByUser(user,pageable);
        List<ProjectResponse> projectList = projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
        return new PageImpl(projectList);
    }

    public Page<ProjectResponse> getProjects(Pageable pageable) {
        User user = securityService.fetchCurrentUser();
        Page<Project> projects = projectRepository.findAllByMembers(user,pageable);
        List<ProjectResponse> projectList = projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
        return new PageImpl(projectList);
    }

    public ResponseEntity<String> deleteProjects(List<Long> projectId) {
        User user = securityService.fetchCurrentUser();
        List<Optional<Project>> projectList = projectId.stream().map(projectRepository::findById).collect(Collectors.toList());
        List<Optional<Project>> projectByUser = projectList.stream()
                .filter(Optional::isPresent)
                .filter(project -> project.get().getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
        for (Optional<Project> project : projectByUser) {
            List<Log> allByProject = logRepository.findAllByProject(project.get());
            allByProject.forEach(log -> logRepository.deleteById(log.getId()));
            projectRepository.deleteById(project.get().getId());
        }
        return ResponseEntity.ok().body("Projects successfully deleted");
    }

  /*  TODO
    public Page<ProjectResponse> searchProjects(LocalDate dateTo, LocalDate dateFrom, String keyword,Pageable pageable) {
        if (dateTo != null && dateFrom != null && keyword != null){
            Page<Project> projects = projectRepository.serachProject(keyword, dateTo, dateFrom, pageable);
            List<ProjectResponse> projectResponse = projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
            return new PageImpl(projectResponse);
        }else if (dateTo == null && dateFrom != null && keyword != null){
                Page<Project> projects = projectRepository.serachProjectFrom(dateFrom, keyword, pageable);
                List<ProjectResponse> projectResponse = projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
                return new PageImpl(projectResponse);
        }else if (dateTo != null && dateFrom == null && keyword != null){
            Page<Project> projects = projectRepository.serachProjectTo(dateTo,keyword, pageable);
            List<ProjectResponse> projectResponse = projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
            return new PageImpl(projectResponse);
        }else if (dateTo != null && dateFrom != null){
            Page<Project> projects = projectRepository.serachProject(dateTo, dateFrom, pageable);
            List<ProjectResponse> projectResponse = projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
            return new PageImpl(projectResponse);
        } else if (keyword != null){
            Page<Project> projects = projectRepository.serachProject(keyword, pageable);
            List<ProjectResponse> projectResponse = projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
            return new PageImpl(projectResponse);
        }else {
            List<Project> all = projectRepository.findAll();
            return new PageImpl(all);
        }
    }
    */
}
