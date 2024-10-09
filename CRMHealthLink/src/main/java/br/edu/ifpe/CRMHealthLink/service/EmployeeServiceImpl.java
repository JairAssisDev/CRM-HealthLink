package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.repository.EmployeeRepository;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IEmployeeService;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }


    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }


    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Employee employeeDto) {
        Employee employee = findById(id);

        updateFields(employeeDto,employee);

        employeeRepository.save(employee);
    }
}
