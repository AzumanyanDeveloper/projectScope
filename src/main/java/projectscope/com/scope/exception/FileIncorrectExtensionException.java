package projectscope.com.scope.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileIncorrectExtensionException extends UserExceptions {
    public FileIncorrectExtensionException(String message) {
        super(String.format("Incorrect file extension: %s", message));
    }
}
