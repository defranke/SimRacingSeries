package com.bazinga.SimRacingSeries_Backend;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleError(Throwable t) {
        return new ResponseError(t.getMessage());
    }

    class ResponseError {
        private String error;

        public ResponseError(String msg) {
            setError(msg);
        }

        public String getError() {
            return error;
        }

        public void setError(String message) {
            this.error = message;
        }
    }

}
