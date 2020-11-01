package projectscope.com.scope.endpoint;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectscope.com.scope.dto.request.ProjectRequest;
import projectscope.com.scope.dto.response.ProjectResponse;
import projectscope.com.scope.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectEndpoint {

    private final ProjectService projectService;

    public ProjectEndpoint(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    @ApiOperation("Add Project")
    public ProjectResponse createProject(@RequestBody ProjectRequest request){
        return projectService.createProject(request);
    }

    @GetMapping("/myCreateProjects")
    @ApiOperation("Get create projects")
    Page<ProjectResponse> getMyProjects(Pageable pageable){
       return projectService.getMyProjects(pageable);
    }

    @GetMapping("/myProjects")
    @ApiOperation("Get projects")
    Page<ProjectResponse> getProjects(Pageable pageable){
        return projectService.getProjects(pageable);
    }

    @DeleteMapping("/delete")
    @ApiOperation("Delete my projects")
    public ResponseEntity<String> deleteLogs(@RequestParam List<Long> projectId){
        return projectService.deleteProjects(projectId);
    }

    /* TODO
    @GetMapping("/search")
    @ApiOperation("Search projects")
    public Page<ProjectResponse> searchProjects(@RequestParam(value = "dateTo",required = false) LocalDate dateTo,
                                                @RequestParam(value = "dateFrom",required = false) LocalDate dateFrom,
                                                @RequestParam(value = "keyword",required = false) String keyword,
                                                Pageable pageable
                                                ){
        return projectService.searchProjects(dateTo,dateFrom,keyword,pageable);
    }

     */
}
