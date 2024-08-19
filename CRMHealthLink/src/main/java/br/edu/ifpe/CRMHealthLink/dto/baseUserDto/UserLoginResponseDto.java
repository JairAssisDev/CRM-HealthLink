package br.edu.ifpe.CRMHealthLink.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
