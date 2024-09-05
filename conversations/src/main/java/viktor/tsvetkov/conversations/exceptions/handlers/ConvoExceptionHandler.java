package viktor.tsvetkov.conversations.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import viktor.tsvetkov.conversations.exceptions.NoUsersToTalkException;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ConvoExceptionHandler {

    @ExceptionHandler(NoUsersToTalkException.class)
    public ResponseEntity<Response> handleNoUsersToTalk(NoUsersToTalkException e) {
        Response response = new Response(e);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response> handleNoSuchElementException(NoSuchElementException e) {
        Response response = new Response(e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
