package br.edu.ifpe.CRMHealthLink.service;

import static org.mockito.Mockito.*;

import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private IUserRepository userRepo;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User mockUser;  // Ajuste aqui para ser do tipo User

    @BeforeEach
    void setUp() {
        mockUser = new User(); // Criando um objeto do tipo User
        mockUser.setEmail("testUser@example.com");  // Usando os métodos específicos da classe User
        mockUser.setPassword("password123");
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Mockando o comportamento do repositório
        when(userRepo.findByUsername("testUser@example.com")).thenReturn(mockUser);

        // Testando o método loadUserByUsername
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser@example.com");

        // Verificando se o usuário retornado é o esperado
        assertNotNull(userDetails);
        assertEquals("testUser@example.com", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Garantindo que o repositório retorne null para o caso do usuário não ser encontrado
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(null);

        // Testando a exceção esperada
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("nonExistentUser"));

        // Verificando a mensagem da exceção (se desejado)
        assertEquals("Usuário não encontrado com o username: nonExistentUser", exception.getMessage());
    }
}

