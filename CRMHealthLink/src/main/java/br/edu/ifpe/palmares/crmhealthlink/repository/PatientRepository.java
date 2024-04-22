package br.edu.ifpe.palmares.crmhealthlink.repository;

import br.edu.ifpe.palmares.crmhealthlink.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient,Long> {
    Optional<Patient> findByCpfAndName(String cpf, String name);
}
