package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Mock
    TokenService tokenService;

    @Mock
    UserService userService;
}
