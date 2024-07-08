package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.UserResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.UserMapper;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/user")
@Tag(name = "Task API", description = "API para  gestão hospitalar")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Cria uma nova tarefa", description = "Cria uma nova tarefa com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<User> create( @RequestBody UserCreateDto user) {
        User responseUser = userService.save(UserMapper.toUserItem(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Operation(summary = "pega todos os usuarios",description = "...")
    @GetMapping
    private ResponseEntity<List<User>> findAll() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Pega o usuario pelo id",description = "...")
    @GetMapping("/{id}")
    private ResponseEntity<UserResponseDto> getUserById(@RequestParam Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDtoUser(user));

    }

    @Operation(summary = "Remove o usuario pelo id",description = "...")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "atualiza o usuario pelo id",description = "...")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(long id , UserCreateDto user) {
        userService.update(id,user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

