package de.htwberlin;

import de.htwberlin.manageGame.export.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleGameDoesNotExistException(GameDoesNotExistException e) {
        GameApiError gameApiError = new GameApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(gameApiError, new HttpHeaders(), gameApiError.getStatus());
    }

    @ExceptionHandler(GameAnswerDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleGameAnswerDoesNotExistException(GameAnswerDoesNotExistException e) {
        GameApiError gameApiError = new GameApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(gameApiError, new HttpHeaders(), gameApiError.getStatus());
    }

    @ExceptionHandler(GameQuestionDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleGameQuestionDoesNotExistException(GameQuestionDoesNotExistException e) {
        GameApiError gameApiError = new GameApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(gameApiError, new HttpHeaders(), gameApiError.getStatus());
    }

    @ExceptionHandler(RoundDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleRoundDoesNotExistException(RoundDoesNotExistException e) {
        GameApiError gameApiError = new GameApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(gameApiError, new HttpHeaders(), gameApiError.getStatus());
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleUserDoesNotExistException(UserDoesNotExistException e) {
        GameApiError gameApiError = new GameApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(gameApiError, new HttpHeaders(), gameApiError.getStatus());
    }



}
