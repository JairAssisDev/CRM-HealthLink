package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    @Autowired
    private IEmployeeRepository IEmployeeRepository;

    @Transactional
    public Employee save(Employee employee) {
        return IEmployeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return IEmployeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return IEmployeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        IEmployeeRepository.deleteById(id);
    }

    @Transactional
    public void update(EmployeeCreateDto employeeCreateDto) {
        Employee employee = IEmployeeRepository.findByEmail(employeeCreateDto.getEmail()).orElseThrow(()->new RuntimeException("Funcionário não encontrado"));

        employee.setName(employeeCreateDto.getName());
        employee.setBirthDate(employeeCreateDto.getBirthDate());
        employee.setEmail(employeeCreateDto.getEmail());
        employee.setCpf(employeeCreateDto.getCpf());
        employee.setAcessLevel(employeeCreateDto.getAcessLevel());

        employee.setPassword(employeeCreateDto.getPassword());
        employee.setOffice(employeeCreateDto.getOffice());
        IEmployeeRepository.save(employee);
    }

    public Optional<Employee> findByEmail(String email) {
        return IEmployeeRepository.findByEmail(email);
    }


}
