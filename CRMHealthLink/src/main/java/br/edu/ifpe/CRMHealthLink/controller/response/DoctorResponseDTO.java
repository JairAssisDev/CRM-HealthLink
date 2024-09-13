package br.edu.ifpe.CRMHealthLink.controller.response;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.DoctorAvailability;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorResponseDTO{

    String name;
    String email;
    String crm;
    String speciality;
    List<AvailabilityResponse> availabilities;

    public DoctorResponseDTO() {
    }

    public DoctorResponseDTO(Doctor doctor,boolean withAvailabilities){
        this.name = doctor.getName();
        this.crm = doctor.getCRM();
        this.email = doctor.getEmail();
        this.speciality = doctor.getSpecialty();

        if(withAvailabilities)
            this.availabilities = doctor.getAvailabilities().stream()
                        .map(a -> new AvailabilityResponse(a.getBeginTime(),a.getBeginTime()))
                        .toList();
    }
}
