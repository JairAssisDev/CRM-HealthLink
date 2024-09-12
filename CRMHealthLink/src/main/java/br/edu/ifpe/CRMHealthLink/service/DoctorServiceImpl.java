package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.repository.DoctorRepository;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IDoctorService;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DoctorServiceImpl implements IDoctorService {


    private final DoctorRepository doctorRepository;
    public DoctorServiceImpl(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor save(Doctor doctor) {
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

}
