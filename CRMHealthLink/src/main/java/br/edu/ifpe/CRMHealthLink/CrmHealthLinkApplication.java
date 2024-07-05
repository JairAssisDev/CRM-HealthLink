package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.entity.Employee;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.repository.EmployeeRepository;
import br.edu.ifpe.CRMHealthLink.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrmHealthLinkApplication {
	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;
	public CrmHealthLinkApplication(UserRepository userRepository,
									EmployeeRepository employeeRepository) {
		this.userRepository = userRepository;
		this.employeeRepository = employeeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CrmHealthLinkApplication.class, args);
	}
	//TESTE
	@Bean
	public CommandLineRunner run(){
		return a->{
			userRepository.save(new User());
			employeeRepository.save(new Employee());
		};
	}
}
