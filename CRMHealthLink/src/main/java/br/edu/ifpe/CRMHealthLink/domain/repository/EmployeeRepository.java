package br.edu.ifpe.CRMHealthLink.domain.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>  {
}
