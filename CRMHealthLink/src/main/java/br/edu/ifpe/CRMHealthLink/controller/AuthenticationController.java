package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/login")
public class AuthenticationController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("teste")
    public String teste(){
        return "ok";
    }
    @GetMapping
    public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user.email(),user.password());
        auth = authenticationManager.authenticate(auth);

        if(!auth.isAuthenticated()){
            return ResponseEntity.badRequest().build();
        }

        User u = new User();
        u.setEmail(user.email());

        return ResponseEntity.ok(tokenService.generateToken(u));
    }

}
