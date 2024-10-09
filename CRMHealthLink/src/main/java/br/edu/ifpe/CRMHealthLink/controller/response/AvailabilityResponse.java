package br.edu.ifpe.CRMHealthLink.controller.response;

import java.time.LocalDateTime;

public record AvailabilityResponse(LocalDateTime beginTime, LocalDateTime endTime) {
}
