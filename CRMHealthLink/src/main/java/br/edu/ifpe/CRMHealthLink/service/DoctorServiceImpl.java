package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.DoctorAvailability;
import br.edu.ifpe.CRMHealthLink.domain.repository.DoctorAvailabilityRepository;
import br.edu.ifpe.CRMHealthLink.domain.repository.DoctorRepository;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IAppointmentService;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IDoctorService;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DoctorServiceImpl implements IDoctorService {
    @Autowired
    private PasswordEncoder encoder;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRepository availabilityRepository;

    private final IAppointmentService appointmentService;
    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             DoctorAvailabilityRepository availabilityRepository,
                             IAppointmentService appointmentService){
        this.doctorRepository = doctorRepository;
        this.availabilityRepository = availabilityRepository;
        this.appointmentService = appointmentService;
    }

    @Transactional
    public Doctor save(Doctor doctor) {
        doctor.setPassword(encoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }


    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    @Transactional
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Doctor doctorDto) {
        Doctor doctor = findById(id);

        updateFields(doctorDto,doctor);

        doctorRepository.save(doctor);
    }

    @Override
    public void addAvailability(DoctorAvailability doctorAvailability) {
        availabilityRepository.save(doctorAvailability);
    }

    @Override
    @Transactional
    public void schedule(LocalDateTime beginTime, LocalDateTime endTime, Doctor doctor, Appointment appointment) {
        DoctorAvailability availability = availabilityRepository
                .findByDoctorAndBeginTimeLessThanEqualAndEndTimeIsGreaterThanEqual(doctor, beginTime, endTime);

        long ticketAmount = availability.getTickets();

        if(ticketAmount > 0)
            availability.setTickets(ticketAmount-1);
        else
            throw new RuntimeException("Dont available");

        appointment.setDate(availability.getBeginTime());
        appointmentService.save(appointment);
        availabilityRepository.save(availability);

    }
}
