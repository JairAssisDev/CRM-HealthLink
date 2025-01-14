package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o username: " + username);
        }
        return user;
    }

}
