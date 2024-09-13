package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.DoctorAvailability;
import br.edu.ifpe.CRMHealthLink.domain.repository.DoctorAvailabilityRepository;
import br.edu.ifpe.CRMHealthLink.domain.repository.DoctorRepository;
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
    private DoctorAvailabilityRepository availabilityRepository;
    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             DoctorAvailabilityRepository availabilityRepository){
        this.doctorRepository = doctorRepository;
        this.availabilityRepository = availabilityRepository;
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
}
