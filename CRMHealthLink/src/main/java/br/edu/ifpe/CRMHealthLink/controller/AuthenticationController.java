package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.config.security.TokenService;
import br.edu.ifpe.CRMHealthLink.controller.request.UserLogin;
import br.edu.ifpe.CRMHealthLink.controller.response.UserLoginResponse;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.domain.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLogin user) {

        Authentication auth = new UsernamePasswordAuthenticationToken(user.email(),user.password());

        authenticationManager.authenticate(auth);

        User u = userService.getUserByEmail(user.email());

        return ResponseEntity.ok(
                new UserLoginResponse(u.getName(),u.getEmail(),
                tokenService.generateToken(u),u.getAcessLevel())
        );
    }



}
