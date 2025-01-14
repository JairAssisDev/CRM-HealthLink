package br.edu.ifpe.CRMHealthLink.service;


import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    public IUserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void getUserByEmail(){
        var user = new User();
        user.setEmail("patient@email.com");
        user.setName("Mocked");

        when(userRepository.findByEmail("patient@email.com"))
                .thenReturn(user);

        User returnedUser = userService.getUserByEmail("patient@email.com");

        assertEquals(returnedUser,user);
        verify(userRepository,times(1)).findByEmail("patient@email.com");
    }

}
