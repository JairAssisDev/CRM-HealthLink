package br.edu.ifpe.CRMHealthLink.dto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String name;

    private Date dayOfBirth;

    private String cpf;

    private AcessLevel level;
}
