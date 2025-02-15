package br.edu.ifpe.CRMHealthLink.controller.handler;

import br.edu.ifpe.CRMHealthLink.controller.dto.exception.ExceptionResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestControllerAdvicer {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpRequest request){
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(LocalDateTime.now(),request.getURI().toString(), ex.getMessage())
        );
    }
}
