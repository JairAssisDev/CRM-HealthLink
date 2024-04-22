package br.edu.ifpe.palmares.crmhealthlink.repository;

import br.edu.ifpe.palmares.crmhealthlink.domain.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
}
