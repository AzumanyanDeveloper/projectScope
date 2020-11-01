package projectscope.com.scope.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends UserExceptions {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandlerControllerAdvice.class);

    public FileNotFoundException(String imagePath, String userPic) {
        super(String.format("Image resource: %s, %s", imagePath, userPic));
    }

    public FileNotFoundException(String message) {
        super("File not found.");
        LOG.error(message);
    }

    public FileNotFoundException(Long id) {
        super("File not found. Id: " + id);
    }
}
