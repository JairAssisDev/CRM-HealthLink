package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.dto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.Employee;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, EmployeeCreateDto employeeCreateDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id: " + id));

        // Checar os atributos do funcionário com base nos dados recebidos no DTO - SE FOR NECESSÁRIO!!!
        employee.setName(employeeCreateDto.getName());
        employee.setBirthDate(employeeCreateDto.getBirthDate());
        employee.setCpf(employeeCreateDto.getCpf());
        employee.setAcessLevel(employeeCreateDto.getAcessLevel());
        employee.setLogin(employeeCreateDto.getLogin());
        employee.setPassword(employeeCreateDto.getPassword());

        employeeRepository.save(employee);
    }
}
