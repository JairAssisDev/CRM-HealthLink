package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.DoctorLoginResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserLoginResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final TokenService tokenService;

    private final  UserService userService;

    private final PatientService patientService;

    private final IUserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final DoctorService doctorService;

    public AuthenticationController(TokenService tokenService, UserService userService, PatientService patientService,
                                    IUserRepository userRepository, AuthenticationManager authenticationManager, DoctorService doctorService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.patientService = patientService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.doctorService = doctorService;
    }

    @PostMapping("login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDTO user) {

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        auth = authenticationManager.authenticate(auth);

        if (!auth.isAuthenticated()) {
            return ResponseEntity.badRequest().build();
        }
        User u = userService.getUserByEmail(user.getEmail());
        switch (u.getAcessLevel()) {
            case PATIENT:
                UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
                userLoginResponseDto.setToken(tokenService.generateToken(u));
                userLoginResponseDto.setId(u.getId());
                userLoginResponseDto.setEmail(u.getEmail());
                userLoginResponseDto.setAcessLevel(u.getAcessLevel());
                userLoginResponseDto.setName(u.getName());

                return ResponseEntity.ok(userLoginResponseDto);
            case DOCTOR:
                Doctor doctor = doctorService.getByEmail(user.getEmail());
                DoctorLoginResponseDto doctorLoginResponseDto = new DoctorLoginResponseDto();
                doctorLoginResponseDto.setToken(tokenService.generateToken(doctor));
                doctorLoginResponseDto.setId(doctor.getId());
                doctorLoginResponseDto.setEmail(doctor.getEmail());
                doctorLoginResponseDto.setAcessLevel(doctor.getAcessLevel());
                doctorLoginResponseDto.setName(doctor.getName());
                doctorLoginResponseDto.setCRM(doctor.getCRM());
                doctorLoginResponseDto.setSpeciality(doctor.getSpeciality());
                return ResponseEntity.ok(doctorLoginResponseDto);

            case MANAGER:
                UserLoginResponseDto managerLoginResponseDto = new UserLoginResponseDto();
                managerLoginResponseDto.setToken(tokenService.generateToken(u));
                managerLoginResponseDto.setId(u.getId());
                managerLoginResponseDto.setEmail(u.getEmail());
                managerLoginResponseDto.setAcessLevel(u.getAcessLevel());
                managerLoginResponseDto.setName(u.getName());
                return ResponseEntity.ok(managerLoginResponseDto);

            case ATTENDANT:
                UserLoginResponseDto attendantLoginResponseDto = new UserLoginResponseDto();
                attendantLoginResponseDto.setToken(tokenService.generateToken(u));
                attendantLoginResponseDto.setId(u.getId());
                attendantLoginResponseDto.setEmail(u.getEmail());
                attendantLoginResponseDto.setAcessLevel(u.getAcessLevel());
                attendantLoginResponseDto.setName(u.getName());
                return ResponseEntity.ok(attendantLoginResponseDto);

        }

        return ResponseEntity.badRequest().build();
    }
}
