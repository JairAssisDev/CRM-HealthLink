package br.edu.ifpe.CRMHealthLink.controller.dto.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(LocalDateTime.now(),request.getRequestURI(),ex.getMessage()));
    }

}
