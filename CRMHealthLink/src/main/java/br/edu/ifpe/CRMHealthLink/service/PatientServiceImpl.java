package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IPatientService;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.service.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder encoder;


    @Transactional
    public Patient save(Patient patient) {
        patient.setPassword(encoder.encode(patient.getPassword()));
        patient.setAcessLevel(AcessLevel.PATIENT);
        return patientRepository.save(patient);
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }


    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }


    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id,Patient patient) {
        Patient patientDB = findById(id);

        updateFields(patient,patientDB);

        patientRepository.save(patientDB);
    }

}