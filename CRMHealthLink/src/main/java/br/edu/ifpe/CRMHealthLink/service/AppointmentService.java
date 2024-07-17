package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
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
    public void update(Long id, AppointmentCreateDto appointmentCreateDto) {
        Appointment appointmentToUpdate = findById(id);

        Appointment updatedAppointment = appointmentMapper.toAppointment(appointmentCreateDto);

        appointmentToUpdate.setDate(updatedAppointment.getDate());
        appointmentToUpdate.setDescription(updatedAppointment.getDescription());
        appointmentToUpdate.setDoctor(updatedAppointment.getDoctor());
        appointmentToUpdate.setPatient(updatedAppointment.getPatient());
        appointmentToUpdate.setEmployee(updatedAppointment.getEmployee());

        appointmentRepository.save(appointmentToUpdate);
    }
}
