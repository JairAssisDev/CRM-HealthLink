package br.edu.ifpe.palmares.crmhealthlink.repository;

import br.edu.ifpe.palmares.crmhealthlink.domain.Pacient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacientRepository extends CrudRepository<Pacient,Long> {

}
