package br.edu.ifpe.palmares.crmhealthlink.repository;

import br.edu.ifpe.palmares.crmhealthlink.domain.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Integer> {
}
