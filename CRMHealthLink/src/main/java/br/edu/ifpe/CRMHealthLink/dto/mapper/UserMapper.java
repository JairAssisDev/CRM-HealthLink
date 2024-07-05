package br.edu.ifpe.CRMHealthLink.dto.mapper;

import br.edu.ifpe.CRMHealthLink.dto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.UserResponseDto;
import br.edu.ifpe.CRMHealthLink.entity.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toUserItem(UserCreateDto createDto){
        return new ModelMapper().map(createDto, User.class);
    }
    public static UserResponseDto toDtoUser(User user){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(user, UserResponseDto.class);
        return modelMapper.map(user, UserResponseDto.class);
    }
    public static List<UserResponseDto> toDtoUsers(List<User> users){
        return users.stream().map(user -> toDtoUser(user)).collect(Collectors.toUnmodifiableList());
    }
}
