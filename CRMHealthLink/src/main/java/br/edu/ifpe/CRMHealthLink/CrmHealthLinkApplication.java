package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.domain.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.List;

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
			manager.setBirthDate(LocalDate.of(1985, 5, 15));
			manager.setPassword(encoder.encode("123"));

			var doctor = new Doctor();
			doctor.setAcessLevel(AcessLevel.DOCTOR);
			doctor.setName("Paulo Muzy");
			doctor.setEmail("doctor@email.com");
			doctor.setBirthDate(LocalDate.of(1989, 4, 9));
			doctor.setCRM("20123-PE");
			doctor.setSpeciality(List.of(Speciality.CLINICOGERAL,Speciality.DERMATOLOGISTA));
			doctor.setPassword(encoder.encode("123"));

			var patient = new Patient();
			patient.setAcessLevel(AcessLevel.PATIENT);
			patient.setName("Moacir Junior");
			patient.setEmail("patient@email.com");
			doctor.setBirthDate(LocalDate.of(2001, 8, 30));
			patient.setPassword(encoder.encode("123"));

			
			userRepository.save(manager);
			userRepository.save(doctor);
			userRepository.save(patient);
		};
	}
}
