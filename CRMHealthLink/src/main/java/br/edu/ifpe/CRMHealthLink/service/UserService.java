package br.edu.ifpe.CRMHealthLink.service;


import br.edu.ifpe.CRMHealthLink.dto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.UserMapper;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.UserRepository;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    public void delete(Long id) {
       User user = userRepository.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("Usuairo não encontrado.")
               );
       userRepository.delete(user);
    }

    @Transactional
    public User update(long id, UserCreateDto newUser) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuairo não encontrado.")
        );
        user.setName(newUser.getName());
        user.setName(newUser.getLogin());
        user.setCpf(newUser.getCpf());
        user.setAcessLevel(newUser.getAcessLevel());
        user.setBirthDate(newUser.getBirthDate());
        user.setPassword(newUser.getPassword());

        return userRepository.save(user);

    }
}
