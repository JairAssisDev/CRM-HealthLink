package br.edu.ifpe.palmares.crmhealthlink;

import br.edu.ifpe.palmares.crmhealthlink.domain.Pacient;
import br.edu.ifpe.palmares.crmhealthlink.repository.PacientRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrmHealthLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmHealthLinkApplication.class, args);

    }

    private PacientRepository pacientRepository;

    public CrmHealthLinkApplication(PacientRepository pacientRepository) {
        this.pacientRepository = pacientRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext cxt){
        return args->{
            Pacient pacient= new Pacient();
            pacient.setName("Jair victor");
            pacient.setCpf("12334455588");
            pacient.setAge(23);

            pacientRepository.save(pacient);
            Iterable<Pacient> pacients= pacientRepository.findAll();
            for(Pacient aPacient:pacients){
                System.out.println(aPacient.getName());
            }
        };
    }
}
