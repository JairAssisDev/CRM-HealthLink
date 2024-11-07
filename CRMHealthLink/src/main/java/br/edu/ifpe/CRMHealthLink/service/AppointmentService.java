package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final SchedulingService schedulingService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Transactional
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> getByDoctorAndPatientAndDateAndInicio(Doctor doctor, Patient patient, LocalDate date, LocalTime inicio) {
        return appointmentRepository.findByDoctorAndPatientAndDateAndInicio(doctor, patient, date,inicio);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointment() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment não encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Transactional
    public void update(AppointmentCreateDto appointmentCreateDto) {
        var doctor = doctorService.getByEmail(appointmentCreateDto.getEmail_doctor());
        var patient = patientService.findByEmail(appointmentCreateDto.getEmail_patient());
        Appointment appointmentToUpdate = appointmentRepository
                .findByDoctorAndPatientAndDateAndInicio(doctor,patient,appointmentCreateDto.getDate(),appointmentCreateDto.getInicio())
                .orElseThrow(()->new RuntimeException("Consulta não encontrada"));

        appointmentToUpdate.setDate(appointmentCreateDto.getDate());
        appointmentToUpdate.setDescription(appointmentCreateDto.getDescription());
        appointmentToUpdate.setDoctor(doctor);
        appointmentToUpdate.setPatient(patient);

        appointmentRepository.save(appointmentToUpdate);
    }
    @Transactional
    public boolean criar(AppointmentCreateDto dto){
        var doctor = doctorService.getByEmail(dto.getEmail_doctor());
        var patient = patientService.findByEmail(dto.getEmail_patient());
        int criado = schedulingService.pegarDisponibilidade(doctor,dto.getDate(),dto.getInicio(),dto.getFim(),dto.getSpeciality());
        if(criado>0){
            var app = dto.toEntity();
            app.setDoctor(doctor);
            app.setPatient(patient);
            appointmentRepository.save(app);
        }

        return criado > 0;
    }

    public List<AppointmentResponseDto> consultasPaciente(String email){
        return appointmentRepository.findByPatient(patientService.findByEmail(email));
    }
}
