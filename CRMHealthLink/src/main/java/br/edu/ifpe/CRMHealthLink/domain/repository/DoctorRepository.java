package br.edu.ifpe.CRMHealthLink.domain.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
