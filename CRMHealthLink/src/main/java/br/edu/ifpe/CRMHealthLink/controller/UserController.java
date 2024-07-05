package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.UserMapper;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


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
}
