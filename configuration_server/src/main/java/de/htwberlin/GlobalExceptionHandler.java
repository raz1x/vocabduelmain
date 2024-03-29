package de.htwberlin;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.*;
import jakarta.persistence.OptimisticLockException;
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

    @ExceptionHandler(VocabDAOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleVocabDAOException(VocabDAOException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(UserDAOPersistenceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleUserDAOPersistenceException(UserDAOPersistenceException e) {
        UserApiError userApiError = new UserApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(userApiError, new HttpHeaders(), userApiError.getStatus());
    }

    @ExceptionHandler(GameDAOPersistenceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleGameDAOPersistenceException(GameDAOPersistenceException e) {
        GameApiError gameApiError = new GameApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(gameApiError, new HttpHeaders(), gameApiError.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        VocabApiError vocabApiError = new VocabApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(vocabApiError, new HttpHeaders(), vocabApiError.getStatus());
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<Object> handleOptimisticLockException(OptimisticLockException e) {
        GameApiError gameApiError = new GameApiError(HttpStatus.CONFLICT, e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<Object>(gameApiError, new HttpHeaders(), gameApiError.getStatus());
    }

}
