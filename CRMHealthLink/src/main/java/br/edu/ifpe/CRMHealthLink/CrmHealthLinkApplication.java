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
											  MockEntities mock){
		return a ->{
			var manager = new Employee();
			manager.setAcessLevel(AcessLevel.MANAGER);
			manager.setEmail("admin");
			manager.setPassword(encoder.encode("123"));
			employeeRepository.save(manager);
			;
			mock.saveAppointment();
			mock.saveAppointment();

			var ap = mock.getAppointment();
			ap.setNotified(true);
			mock.saveAppointment(ap);

			LocalDateTime start = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0);
			LocalDateTime end = start.plusDays(1);
			System.out.println(appointmentRepository.findByDateBetweenAndNotifiedIsFalse(start,end));
		};
	}
}
