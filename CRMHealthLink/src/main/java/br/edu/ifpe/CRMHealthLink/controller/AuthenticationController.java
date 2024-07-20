package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
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
    private AuthenticationManager authenticationManager;


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        auth = authenticationManager.authenticate(auth);

        if(!auth.isAuthenticated()){
            return ResponseEntity.badRequest().build();
        }

        User u = new User();
        u.setEmail(user.getEmail());
        return ResponseEntity.ok(tokenService.generateToken(u));
    }

    @PostMapping("create/patient")
    public ResponseEntity createPatient(@RequestBody @Valid PatientCreateDto patient){
        if(userService.getUserByEmail(patient.getEmail()) != null){
           return ResponseEntity.badRequest().body("User already exists!");
        }
        patient.setAcessLevel(AcessLevel.PATIENT);
        patientService.save(patient);
        return ResponseEntity.ok().build();
    }

}
