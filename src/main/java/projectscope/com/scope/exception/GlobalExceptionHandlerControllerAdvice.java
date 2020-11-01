package projectscope.com.scope.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception exception) {
        log.error("Message: {}\n{}", exception.getMessage(), exception);
        if (isExceptionInWhiteList(exception)) {
            final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
            int code = 500;
            if (responseStatus != null) {
                code = responseStatus.value().value();
            }
            return new ResponseEntity<>(exception.getMessage(),
                    HttpStatus.valueOf(code));
        }
        return new ResponseEntity<>("Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void badRequest(HttpServletResponse response, Exception e) throws Exception {
        response.getWriter().print(e.getMessage());
        log.error(e.getMessage(), e);
    }
    @ExceptionHandler({MessagingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void internalServerError(HttpServletResponse response, Exception e) throws Exception {
        response.getWriter().print(e.getMessage());
        log.error(e.getMessage(), e);
    }

    private Boolean isExceptionInWhiteList(Exception exception) {
        return exception instanceof UserExceptions;
    }
}
