package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class CrmHealthLinkApplication {
	private final PatientRepository patientRepository;
	private final EmployeeRepository employeeRepository;
	private final AppointmentRepository appointmentRepository;
	private final DoctorRepository doctorRepository;

	public CrmHealthLinkApplication(PatientRepository patientRepository,
									EmployeeRepository employeeRepository,
									AppointmentRepository appointmentRepository,
									DoctorRepository doctorRepository){
		this.employeeRepository = employeeRepository;
		this.patientRepository = patientRepository;
		this.appointmentRepository = appointmentRepository;
		this.doctorRepository = doctorRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CrmHealthLinkApplication.class, args);
	}
	//TESTE
	@Bean
	public CommandLineRunner run(){
		return a->{
			Patient p1 = new Patient();
			p1.setCpf("123");
			p1.setName(("One"));

			Employee e1 = new Employee();
			e1.setName("Fulano");

			Doctor d1 = new Doctor();
			d1.setName("DR.Ciclano");

			patientRepository.save(p1);
			employeeRepository.save(e1);
			doctorRepository.save(d1);

			Appointment a1 = new Appointment();
			a1.setDate(LocalDateTime.now());
			a1.setPatient(p1);
			a1.setDoctor(d1);
			a1.setEmployee(e1);
			a1.setDescription("Blablbal fsifnsod");

			appointmentRepository.save(a1);
		};
	}
}
