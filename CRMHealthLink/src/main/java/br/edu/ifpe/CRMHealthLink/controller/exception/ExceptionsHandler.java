package br.edu.ifpe.CRMHealthLink.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFound(UserNotFoundException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        e.getClass().getSimpleName(),
                        e.getMessage(),
                        request.getRequestURI()
                )
        );
    }
    @ExceptionHandler(IncorrectInputException.class)
    public ResponseEntity<ErrorResponse> incorrectInput(IncorrectInputException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        e.getClass().getSimpleName(),
                        e.getMessage(),
                        request.getRequestURI()
                )
        );
    }
}
