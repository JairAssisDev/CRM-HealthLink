package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.repository.AppointmentRepository;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IAppointmentService;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }


    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

    @Transactional
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Appointment appointmentDto) {
        Appointment appointment = findById(id);

        updateFields(appointmentDto,appointment);

        appointmentRepository.save(appointment);
    }



}
