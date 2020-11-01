package projectscope.com.scope.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends UserExceptions {
    public UserNotFoundException(String email) {
        super("User not found, email:" + email);
    }

    public UserNotFoundException(Long id) {
        super("User not found, id:" + id);
    }
}
