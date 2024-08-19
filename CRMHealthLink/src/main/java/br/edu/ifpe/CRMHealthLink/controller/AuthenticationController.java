package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserLoginResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.UserRepository;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDTO user) {

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        auth = authenticationManager.authenticate(auth);

        if(!auth.isAuthenticated()){
            return ResponseEntity.badRequest().build();
        }
        User u = userService.getUserByEmail(user.getEmail());
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        userLoginResponseDto.setToken(tokenService.generateToken(u));
        userLoginResponseDto.setId(u.getId());
        userLoginResponseDto.setEmail(u.getEmail());
        userLoginResponseDto.setAcessLevel(u.getAcessLevel());
        userLoginResponseDto.setName(u.getName());

        return ResponseEntity.ok(userLoginResponseDto);
    }



}
