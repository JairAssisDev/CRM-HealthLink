package br.edu.ifpe.CRMHealthLink.controller.response;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;

public record UserLoginResponse(String name, String email, String token, AcessLevel level) {
}
