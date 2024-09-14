package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.exception.IncorrectInputException;
import br.edu.ifpe.CRMHealthLink.controller.exception.UserNotFoundException;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email){

        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
    }

    public <T> T getUserByEmail(String email,Class<T> tClass) {
        var u = getUserByEmail(email);

        if (!tClass.isInstance(u)) {
            throw new IncorrectInputException(String.format("Email: [%s] is not %s", email, tClass.getSimpleName()));
        }

        return (T) u;
    }
}
