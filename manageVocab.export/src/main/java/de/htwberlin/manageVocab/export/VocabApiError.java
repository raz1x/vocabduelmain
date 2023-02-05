package de.htwberlin.manageVocab.export;

import org.springframework.http.HttpStatus;

public class VocabApiError {

    private HttpStatus status;

    private String exceptionClass;

    private String message;

    public VocabApiError() {
    }

    public VocabApiError(HttpStatus status, String exceptionClass, String message) {
        super();
        this.status = status;
        this.exceptionClass = exceptionClass;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

}
