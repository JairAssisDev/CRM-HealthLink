package br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {

        private Long id;

        private String name;

        private String email;

        private AcessLevel acessLevel;

        private String token;

}
