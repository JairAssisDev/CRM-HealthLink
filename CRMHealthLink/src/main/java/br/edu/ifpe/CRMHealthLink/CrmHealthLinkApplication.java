package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.*;
import br.edu.ifpe.CRMHealthLink.service.MockEntities;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class CrmHealthLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmHealthLinkApplication.class, args);
	}

	@Bean
	public CommandLineRunner temporaryManager(EmployeeRepository employeeRepository,
											  AppointmentRepository appointmentRepository,
											  PasswordEncoder encoder,
											  MockEntities mock, UserRepository userRepository,DoctorRepository doctorRepository) {
		return a ->{
			var manager = new Employee();
			manager.setAcessLevel(AcessLevel.MANAGER);
			manager.setEmail("admin@email.com");
			manager.setPassword(encoder.encode("123"));

			var doctor = new Doctor();
			doctor.setAcessLevel(AcessLevel.DOCTOR);
			doctor.setEmail("doctor@email.com");
			doctor.setPassword(encoder.encode("123"));

			userRepository.save(doctor);
			employeeRepository.save(manager);
			mock.saveAppointment();
			mock.saveAppointment();

			var ap = mock.getAppointment();
			ap.setDoctor(doctor);
			ap.setNotified(false);
			mock.saveAppointment(ap);
		};
	}
}
