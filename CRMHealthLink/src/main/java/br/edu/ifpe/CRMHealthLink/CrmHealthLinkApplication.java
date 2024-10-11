package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import br.edu.ifpe.CRMHealthLink.repository.IDoctorRepository;
import br.edu.ifpe.CRMHealthLink.repository.IEmployeeRepository;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.service.MockEntities;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CrmHealthLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmHealthLinkApplication.class, args);
	}

	@Bean
	public CommandLineRunner temporaryManager(IEmployeeRepository IEmployeeRepository,
											  IAppointmentRepository IAppointmentRepository,
											  PasswordEncoder encoder,
											  MockEntities mock, IUserRepository IUserRepository, IDoctorRepository IDoctorRepository) {
		return a ->{
			var manager = new Employee();
			manager.setAcessLevel(AcessLevel.MANAGER);
			manager.setName("Jão Jorge");
			manager.setEmail("admin@email.com");
			manager.setPassword(encoder.encode("123"));

			var emp = new Employee();
			emp.setAcessLevel(AcessLevel.ATTENDANT);
			emp.setName("Carmen Jurema");
			emp.setEmail("att@email.com");
			emp.setPassword(encoder.encode("123"));

			var doctor = new Doctor();
			doctor.setAcessLevel(AcessLevel.DOCTOR);
			doctor.setName("Anastasia Inés");
			doctor.setEmail("doctor@email.com");
			doctor.setPassword(encoder.encode("123"));



			IUserRepository.save(doctor);
			IEmployeeRepository.save(manager);
			IEmployeeRepository.save(emp);
			mock.saveAppointment();
			mock.saveAppointment();

			var ap = mock.getAppointment();
			ap.setDoctor(doctor);
			ap.setNotified(false);
			mock.saveAppointment(ap);
		};
	}
}
