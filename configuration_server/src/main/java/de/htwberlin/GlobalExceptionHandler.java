package de.htwberlin;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserApiError;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.userManager.export.WrongPasswordException;
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

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<Object> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.CONFLICT, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(TranslationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleTranslationNotFoundException(TranslationNotFoundException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(VocabListAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<Object> handleVocabListAlreadyExistsException(VocabListAlreadyExistsException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.CONFLICT, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(VocabListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleVocabListNotFoundException(VocabListNotFoundException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(VocabNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleVocabNotFoundException(VocabNotFoundException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        UserApiError userApiError = new UserApiError(HttpStatus.CONFLICT, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(userApiError, new HttpHeaders(), userApiError.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        UserApiError userApiError = new UserApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(userApiError, new HttpHeaders(), userApiError.getStatus());
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException e) {
        UserApiError userApiError = new UserApiError(HttpStatus.UNAUTHORIZED, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(userApiError, new HttpHeaders(), userApiError.getStatus());
    }
}
