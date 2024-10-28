package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final IPatientRepository patientRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Patient save(Patient patient) {
        patient.setAcessLevel(AcessLevel.PATIENT);
        patient.setPassword(encoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }

    public Optional<Patient> getByEmail(String email){
        return patientRepository.findByEmail(email);
    }

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encomtrado"));
    }

    public Patient findByNameAndEmail(String name,String email) {
        try {
            return patientRepository.findByNameAndEmail(name, email);
        } catch (Exception e) {
            throw new RuntimeException("Paciente não encomtrado");
        }
    }
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Transactional
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public void update(Patient patientNew) {
        Patient patient = patientRepository.findByEmail(patientNew.getEmail()).orElseThrow(()->new RuntimeException("Paciente não encontrado"));

        patient.setName(patientNew.getName());
        patient.setBirthDate(patientNew.getBirthDate());
        patient.setEmail(patientNew.getEmail());
        patient.setCpf(patientNew.getCpf());

        if (patientNew.getPassword() != null) {
            patient.setPassword(encoder.encode(patientNew.getPassword()));
        }

        patientRepository.save(patient);

    }


}