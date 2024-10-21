package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IDoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final IDoctorRepository doctorRepository;


    @Transactional
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> getByCRM(String crm){
        return doctorRepository.findByCRM(crm);
    }

    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, DoctorCreateDto doctorCreateDto) {
        Doctor doctor = findById(id);

        doctor.setName(doctorCreateDto.getName());
        doctor.setBirthDate(doctorCreateDto.getBirthDate());
        doctor.setCpf(doctorCreateDto.getCpf());
        doctor.setEmail(doctorCreateDto.getEmail());
        doctor.setAcessLevel(doctorCreateDto.getAcessLevel());

        doctor.setPassword(doctorCreateDto.getPassword());
        doctor.setCRM(doctorCreateDto.getCRM());
        doctor.setSpeciality(doctorCreateDto.getSpeciality());

        doctorRepository.save(doctor);
    }

    public List<Doctor> findAllDoctorBySpecialty(Speciality speciality) {
        return doctorRepository.findAllBySpeciality(speciality);
    }
}
