package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final IPatientRepository IPatientRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Patient save(Patient patient) {
        patient.setAcessLevel(AcessLevel.PATIENT);
        patient.setPassword(encoder.encode(patient.getPassword()));
        return IPatientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public List<Patient> getAllPatient() {
        return IPatientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient findById(Long id) {
        return IPatientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encomtrado"));
    }

    @Transactional
    public void delete(Long id) {
        IPatientRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id,PatientCreateDto patientCreateDto) {
        Patient patient = findById(id);

        patient.setName(patientCreateDto.getName());
        patient.setBirthDate(patientCreateDto.getBirthDate());
        patient.setEmail(patientCreateDto.getEmail());
        patient.setCpf(patientCreateDto.getCpf());

        patient.setPassword(patientCreateDto.getPassword());

        IPatientRepository.save(patient);

    }


}