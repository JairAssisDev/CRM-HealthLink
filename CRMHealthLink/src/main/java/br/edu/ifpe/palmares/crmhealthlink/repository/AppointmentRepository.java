package br.edu.ifpe.palmares.crmhealthlink.repository;

import br.edu.ifpe.palmares.crmhealthlink.domain.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
