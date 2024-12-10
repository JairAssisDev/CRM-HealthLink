package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.AssociateDoctorDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import br.edu.ifpe.CRMHealthLink.repository.ISchedulingRepository;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import br.edu.ifpe.CRMHealthLink.service.SchedulingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class CrmHealthLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmHealthLinkApplication.class, args);
	}

	@Bean
	public CommandLineRunner temporaryManager(PasswordEncoder encoder
											  , IUserRepository userRepository,
											  SchedulingService schedulingService,
											  AppointmentService appointmentService) {
		return a ->{
			var manager = new Employee();
			manager.setAcessLevel(AcessLevel.MANAGER);
			manager.setOffice(Office.MANAGER);
			manager.setName("Jair Assis");
			manager.setEmail("admin@email.com");
			manager.setBirthDate(LocalDate.of(1985, 5, 15));
			manager.setPassword(encoder.encode("123"));

			var attendant = new Employee();
			attendant.setAcessLevel(AcessLevel.ATTENDANT);
			attendant.setOffice(Office.RECEPTIONIST);
			attendant.setName("Lucas Manoel");
			attendant.setEmail("att@email.com");
			attendant.setBirthDate(LocalDate.of(1999, 9, 4));
			attendant.setPassword(encoder.encode("123"));

			var doctor = new Doctor();
			doctor.setAcessLevel(AcessLevel.DOCTOR);
			doctor.setName("Dr. Matheus");
			doctor.setEmail("doctor@email.com");
			doctor.setBirthDate(LocalDate.of(1989, 4, 9));
			doctor.setCRM("20123-PE");
			doctor.setSpeciality(List.of(Speciality.CLINICOGERAL,Speciality.DERMATOLOGISTA));
			doctor.setPassword(encoder.encode("123"));

			var patient = new Patient();
			patient.setAcessLevel(AcessLevel.PATIENT);
			patient.setName("Lucas Patrick");
			patient.setEmail("patient@email.com");
			patient.setBirthDate(LocalDate.of(2001, 8, 30));
			patient.setPassword(encoder.encode("123"));

			userRepository.save(manager);
			userRepository.save(attendant);
			userRepository.save(doctor);
			userRepository.save(patient);

			var inicio = LocalTime.now().plusHours(2).truncatedTo(ChronoUnit.MINUTES);
			var schedulingDTO = SchedulingCreateDTO.builder()
					.vagas(1)
					.date(LocalDate.now().plusDays(2))
					.homeTime(inicio)
					.endTime(inicio.plusHours(1))
					.tipoAgendamento(TipoAgendamento.CONSULTA_ONLINE)
					.specialityType(Speciality.CLINICOGERAL)
					.build();
			schedulingService.criarAgenda(schedulingDTO);
			var associarMedico = new AssociateDoctorDTO();
			associarMedico.setCrm("20123-PE");
			associarMedico.setDate(LocalDate.now().plusDays(2));
			associarMedico.setTempoMedioConsultaMinutos(10);
			associarMedico.setHomeTime(inicio);
			associarMedico.setEndTime(inicio.plusHours(1));
			associarMedico.setSpecialityType(Speciality.CLINICOGERAL);

			schedulingService.scheduleDoctor(associarMedico);
			var appDTO = AppointmentCreateDto.builder()
					.email_doctor("doctor@email.com")
					.email_patient("patient@email.com")
					.inicio(inicio)
					.fim(inicio.plusMinutes(10))
					.speciality(Speciality.CLINICOGERAL)
					.date(LocalDate.now().plusDays(2))
					.build();

			appointmentService.criar(appDTO);


		};
	}
}
