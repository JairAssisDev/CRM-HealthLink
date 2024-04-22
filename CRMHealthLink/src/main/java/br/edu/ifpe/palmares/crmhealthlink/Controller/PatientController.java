package br.edu.ifpe.palmares.crmhealthlink.Controller;

import br.edu.ifpe.palmares.crmhealthlink.repository.PatientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacient")
public class PatientController {

    private PatientRepository patientRepository;


    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/pagina")
    public String paginaExemplo() {
        return patientRepository.findAll().toString();

    }

}
