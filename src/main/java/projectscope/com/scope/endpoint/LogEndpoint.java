package projectscope.com.scope.endpoint;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectscope.com.scope.dto.request.LogRequest;
import projectscope.com.scope.dto.request.ProjectRequest;
import projectscope.com.scope.dto.response.LogResponse;
import projectscope.com.scope.dto.response.ProjectResponse;
import projectscope.com.scope.service.LogService;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogEndpoint {

    private final LogService logService;

    public LogEndpoint(LogService logService) {
        this.logService = logService;
    }


    @PostMapping("/create")
    @ApiOperation("Add Log")
    public LogResponse createLog(@RequestBody LogRequest request){
        return logService.createLog(request);
    }

    @GetMapping("/myLogs")
    @ApiOperation("Get Logs")
    Page<LogResponse> getMyLogs(Pageable pageable){
       return logService.getMyLogs(pageable);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete my log")
    public ResponseEntity<String> deleteLogs(@PathVariable("id") Long logId){
       return logService.deleteLogs(logId);
    }
}
