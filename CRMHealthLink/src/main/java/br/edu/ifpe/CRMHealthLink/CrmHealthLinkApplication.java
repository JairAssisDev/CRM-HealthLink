package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class CrmHealthLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmHealthLinkApplication.class, args);
	}

	@Bean
	public CommandLineRunner temporaryManager(EmployeeRepository repo){
		return a ->{
			Employee e = new Employee();
			e.setName("Fulano Silva");
			e.setEmail("fulano@example.com");
			var encoder = new BCryptPasswordEncoder();
			e.setPassword(encoder.encode("123"));
			e.setAcessLevel(AcessLevel.MANAGER);
			System.out.println(e.getAuthorities());
			repo.save(e);
		};
	}
}
