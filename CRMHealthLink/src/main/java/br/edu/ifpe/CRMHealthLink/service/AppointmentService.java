package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final IAppointmentRepository IAppointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public Appointment save(Appointment appointment) {
        return IAppointmentRepository.save(appointment);
    }

    public Optional<Appointment> getByDoctorAndPatientAndDate(Doctor doctor, Patient patient, LocalDateTime date ) {
        return IAppointmentRepository.findByDoctorAndPatientAndDate(doctor, patient, date);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointment() {
        return IAppointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Appointment findById(Long id) {
        return IAppointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment não encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        IAppointmentRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, AppointmentCreateDto appointmentCreateDto) {
        Appointment appointmentToUpdate = findById(id);

        Appointment updatedAppointment = appointmentMapper.toAppointment(appointmentCreateDto);

        appointmentToUpdate.setDate(updatedAppointment.getDate());
        appointmentToUpdate.setDescription(updatedAppointment.getDescription());
        appointmentToUpdate.setDoctor(updatedAppointment.getDoctor());
        appointmentToUpdate.setPatient(updatedAppointment.getPatient());
        appointmentToUpdate.setEmployee(updatedAppointment.getEmployee());

        IAppointmentRepository.save(appointmentToUpdate);
    }
}
