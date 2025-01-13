package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.DoctorLoginResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserLoginResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import org.modelmapper.ModelMapper;

import java.util.List;

public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static UserLoginResponseDto fromUser(User user){
        UserLoginResponseDto dto = null;
        switch (user.getAcessLevel()){
            case DOCTOR:
                dto = modelMapper.map(user, DoctorLoginResponseDto.class);
                break;
            default:
                dto = modelMapper.map(user,UserLoginResponseDto.class);
                break;
        }
        return dto;
    }
}
