package br.edu.ifpe.palmares.crmhealthlink.Controller;

import br.edu.ifpe.palmares.crmhealthlink.domain.Appointment;
import br.edu.ifpe.palmares.crmhealthlink.domain.Exam;
import br.edu.ifpe.palmares.crmhealthlink.domain.Patient;
import br.edu.ifpe.palmares.crmhealthlink.domain.Schedule;
import br.edu.ifpe.palmares.crmhealthlink.repository.AppointmentRepository;
import br.edu.ifpe.palmares.crmhealthlink.repository.ExamRepository;
import br.edu.ifpe.palmares.crmhealthlink.repository.PatientRepository;
import br.edu.ifpe.palmares.crmhealthlink.repository.ScheduleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/attendant")
public class AttendantController {
    private PatientRepository patientRepository;
    private ScheduleRepository scheduleRepository;
    private ExamRepository examRepository;
    private AppointmentRepository appointmentRepository;
    public AttendantController(PatientRepository patientRepository,
                               ScheduleRepository scheduleRepository,
                               ExamRepository examRepository,
                               AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.scheduleRepository = scheduleRepository;
        this.examRepository = examRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/agendar/appointment")
    public boolean agendarConsulta(String cpf_patient, String name_patient){
        Optional<Patient> patient = patientRepository.findByCpfAndName(cpf_patient,name_patient);
        if(patient.isPresent()){
            Schedule schedule = new Schedule();
            schedule.setPatient(patient.get());
            Appointment appointment = new Appointment();
            schedule.setHealthService(appointment);
            appointmentRepository.save(appointment);
            scheduleRepository.save(schedule);
            return true;
        }
        return false;
    }
    @GetMapping("/agendar/exam")
    public boolean agendarExam(String cpf_patient, String name_patient){
        Optional<Patient> patient = patientRepository.findByCpfAndName(cpf_patient,name_patient);
        if(patient.isPresent()){
            Schedule schedule = new Schedule();
            schedule.setPatient(patient.get());
            Exam exam = new Exam();
            schedule.setHealthService(exam);
            examRepository.save(exam);
            scheduleRepository.save(schedule);
            return true;
        }
        return false;
    }
}
