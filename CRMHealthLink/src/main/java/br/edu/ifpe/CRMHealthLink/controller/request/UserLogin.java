package br.edu.ifpe.CRMHealthLink.controller.request;

import jakarta.validation.constraints.NotBlank;

public record UserLogin(@NotBlank String email, @NotBlank String password) {
}
