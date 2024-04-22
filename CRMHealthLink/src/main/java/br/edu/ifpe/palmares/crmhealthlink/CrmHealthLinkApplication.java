package br.edu.ifpe.palmares.crmhealthlink;


import br.edu.ifpe.palmares.crmhealthlink.domain.*;
import br.edu.ifpe.palmares.crmhealthlink.repository.EmployeeRepository;
import br.edu.ifpe.palmares.crmhealthlink.repository.PatientRepository;
import br.edu.ifpe.palmares.crmhealthlink.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CrmHealthLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmHealthLinkApplication.class, args);

    }

    private PatientRepository patientRepository;

    public CrmHealthLinkApplication(PatientRepository patientRepository,
                                    EmployeeRepository userRepository) {
        this.patientRepository = patientRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext cxt, UserRepository userRepository,
                                               EmployeeRepository employeeRepository){
        return args->{

            Patient pacient= new Patient();
            pacient.setName("Jair victor");
            pacient.setCpf("12334455588");
            pacient.setBirthDate(Date.valueOf("2004-05-14"));

            patientRepository.save(pacient);

        };
    }
}
