package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserLoginResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.UserMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;
    private final  UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDTO user) {

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        auth = authenticationManager.authenticate(auth);

        User u = userService.getUserByEmail(user.getEmail());
        var dtoResponse = UserMapper.fromUser(u);
        dtoResponse.setToken(tokenService.generateToken(u));
        return ResponseEntity.ok(dtoResponse);
    }
}
