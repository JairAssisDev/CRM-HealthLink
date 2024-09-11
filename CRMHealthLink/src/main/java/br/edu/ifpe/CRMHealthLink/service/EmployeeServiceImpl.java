package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.service.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.EmployeeMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public Employee save(EmployeeCreateDto employee) {
        Employee e = EmployeeMapper.toEmployee(employee);
        return employeeRepository.save(e);
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
        Employee employee = findById(id);

        employee.setName(employeeCreateDto.getName());
        employee.setBirthDate(employeeCreateDto.getBirthDate());
        employee.setEmail(employeeCreateDto.getEmail());
        employee.setCpf(employeeCreateDto.getCpf());
        employee.setAcessLevel(employeeCreateDto.getAcessLevel());

        employee.setPassword(employeeCreateDto.getPassword());
        employee.setOffice(employeeCreateDto.getOffice());
        employeeRepository.save(employee);
    }
}
