package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.domain.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
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
	public CommandLineRunner temporaryManager(PasswordEncoder encoder
											  , IUserRepository userRepository) {
		return a ->{
			var manager = new Employee();
			manager.setAcessLevel(AcessLevel.MANAGER);
			manager.setName("Jão Jorge");
			manager.setEmail("admin@email.com");
			manager.setPassword(encoder.encode("123"));

			var doctor = new Doctor();
			doctor.setAcessLevel(AcessLevel.DOCTOR);
			doctor.setName("Paulo Muzy");
			doctor.setEmail("doctor@email.com");
			doctor.setCRM("20123-PE");
			doctor.setWorkload(10L);
			doctor.setNumberTimeSlots();
			doctor.setSpeciality(Speciality.CLINICOGERAL);
			doctor.setPassword(encoder.encode("123"));

			var patient = new Patient();
			patient.setAcessLevel(AcessLevel.PATIENT);
			patient.setName("Moacir Junior");
			patient.setEmail("patient@email.com");
			patient.setPassword(encoder.encode("123"));

			
			userRepository.save(manager);
			userRepository.save(doctor);
			userRepository.save(patient);
		};
	}
}
