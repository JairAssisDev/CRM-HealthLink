package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;

public class ProntidaoMapper {

    public static ProntidaoResponseDTO response(Prontidao prontidao){
        return new ProntidaoResponseDTO(prontidao.getData(),prontidao.getInicio(),prontidao.getFim(),DoctorMapper.toDtoDoctor(prontidao.getDoctor()));
    }
}
