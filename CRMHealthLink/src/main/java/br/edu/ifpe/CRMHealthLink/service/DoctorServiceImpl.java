package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.useCase.IDoctorService;
import br.edu.ifpe.CRMHealthLink.service.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.domain.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;


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


    //don't repeat yourself.this method already exists in patientServiceImpl
    public static void updateFields(Object source, Object mod){

        List<Class> classes = new ArrayList<>();
        Class currentClass = source.getClass();
        while(currentClass!=null){
            if(currentClass != Object.class)
                classes.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }

        for(Class clasz : classes){
            for(Field field: clasz.getDeclaredFields()){
                try {
                    field.setAccessible(true);
                    var fieldDTO = field.get(source);
                    if( fieldDTO != null){
                        field.set(mod,fieldDTO);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
