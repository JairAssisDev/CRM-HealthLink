package br.edu.ifpe.CRMHealthLink.controller;


import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;


@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/user")
@Tag(name = "Task API", description = "API para  gestão hospitalar")
public class UsersController {
    private final UsersService usersService;



    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User responseUser = usersService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
