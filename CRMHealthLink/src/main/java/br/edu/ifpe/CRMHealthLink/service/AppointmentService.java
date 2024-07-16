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

    @Transactional
    public Appointment save(Appointment appointment) {return appointmentRepository.save(appointment);}


    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointment() {return appointmentRepository.findAll();}

    @Transactional(readOnly = true)
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment não encontrado"));
    }

    @Transactional
    public void delete(Long id) {appointmentRepository.deleteById(id);}

    @Transactional
    public void update(Long id, AppointmentCreateDto appointmentCreateDto) {
        Appointment appointmentToUpdate = findById(id);


        Appointment appointment = AppointmentMapper.toAppointment(appointmentCreateDto);

        appointmentToUpdate.setDate(appointment.getDate());
        appointmentToUpdate.setDescription(appointment.getDescription());
        appointmentToUpdate.setDoctor(appointment.getDoctor());
        appointmentToUpdate.setPatient(appointment.getPatient());
        appointmentToUpdate.setEmployee(appointment.getEmployee());

        appointmentRepository.save(appointmentToUpdate);
    }
}
