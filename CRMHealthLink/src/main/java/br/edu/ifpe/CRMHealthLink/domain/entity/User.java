package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)

public  class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column
    private LocalDate birthDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "cpf" , unique = true)
    private String cpf;

    @Column(name = "acess_level")
    @Enumerated(EnumType.STRING)
    private AcessLevel acessLevel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        switch (acessLevel){
            case MANAGER:
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            case ATTENDANT:
                authorities.add(new SimpleGrantedAuthority("ROLE_ATTENDANT"));
                break;
            case DOCTOR:
                authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
            case PATIENT:
                authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
                break;
        }
        return authorities;
    }
    @Override
    public String getPassword(){
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @PrePersist
    public void cpfVal(){
        if(!ValidadorCPF.isCPF(cpf))
            throw new RuntimeException("CPF Inválido");
    }

    @PreUpdate
    public void cpfValUpdate(){
        if(!ValidadorCPF.isCPF(cpf))
            throw new RuntimeException("CPF Inválido");
    }
}
