package br.edu.ifpe.palmares.crmhealthlink.Controller;


import br.edu.ifpe.palmares.crmhealthlink.domain.Employee;
import br.edu.ifpe.palmares.crmhealthlink.domain.Users;
import br.edu.ifpe.palmares.crmhealthlink.repository.EmployeeRepository;
import br.edu.ifpe.palmares.crmhealthlink.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private UserRepository userRepository;
    private EmployeeRepository employeeRepository;


    public ManagerController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/hireEmployee")
    public boolean hireEmployee(@RequestBody Employee employee){
        //IF USUARIO.LOGADO.ROLE == 2
        Employee e = employeeRepository.save(employee);
        if(null != e.getId()){
            return true;
        }
        return false;
    }
}
