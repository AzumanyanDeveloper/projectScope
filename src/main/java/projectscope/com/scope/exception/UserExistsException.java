package projectscope.com.scope.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserExistsException extends UserExceptions {
    public UserExistsException(String email) {
        super(String.format("User with email: %s already exists", email));
    }
}
