package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.Patient;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encomtrado"));
    }

    @Transactional
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id,PatientCreateDto patientCreateDto) {
        Patient patient = findById(id);

        patient.setName(patientCreateDto.getName());
        patient.setBirthDate(patientCreateDto.getBirthDate());
        patient.setEmail(patientCreateDto.getEmail());
        patient.setCpf(patientCreateDto.getCpf());
        patient.setLogin(patientCreateDto.getLogin());
        patient.setPassword(patientCreateDto.getPassword());

        patientRepository.save(patient);

    }


}