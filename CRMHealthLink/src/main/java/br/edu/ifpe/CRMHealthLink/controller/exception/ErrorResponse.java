package br.edu.ifpe.CRMHealthLink.controller.exception;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, String error, String message, String path) {
}
