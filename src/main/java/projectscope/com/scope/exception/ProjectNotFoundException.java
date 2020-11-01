package projectscope.com.scope.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends ProjectExceptions {

    public ProjectNotFoundException(Long id) {
        super("Project not found, id:" + id);
    }
}
