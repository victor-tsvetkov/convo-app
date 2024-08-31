package viktor.tsvetkov.conversations.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import viktor.tsvetkov.conversations.exceptions.NoUsersToTalkException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(NoUsersToTalkException.class)
    public ResponseEntity<String> handleException(NoUsersToTalkException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response.getMessage(), HttpStatus.CONFLICT);
    }
}
