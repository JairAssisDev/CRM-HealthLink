package br.edu.ifpe.CRMHealthLink.controller.dto.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    public LocalDateTime timestamp;
    public String uri;
    public String message;

    public ExceptionResponse(LocalDateTime timestamp, String uri, String message) {
        this.timestamp = timestamp;
        this.uri = uri;
        this.message = message;
    }
}
