package projectscope.com.scope.exception;

public class UserExceptions extends RuntimeException {
    public UserExceptions(String message) {
        super(message);
    }

    public UserExceptions() {
        super();
    }
}
