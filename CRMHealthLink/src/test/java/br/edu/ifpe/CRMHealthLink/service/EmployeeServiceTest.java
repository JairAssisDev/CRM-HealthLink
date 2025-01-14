package br.edu.ifpe.CRMHealthLink.service;


import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;


import br.edu.ifpe.CRMHealthLink.domain.entity.Office;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    public IEmployeeRepository employeeRepository;
    @Mock
    public PasswordEncoder encoder;
    @InjectMocks
    public EmployeeService employeeService;

    @Test
    public void save(){
        var employee = new Employee();
        employee.setPassword("password");
        employee.setEmail("email");

        when(encoder.encode("password")).thenReturn("encodedPassword");
        when(employeeRepository.save(employee)).thenReturn(employee);

        var returnedEmp = employeeService.save(employee);

        assertEquals(returnedEmp,employee);
        assertEquals(returnedEmp.getPassword(),"encodedPassword");
        verify(encoder,times(1)).encode("password");
        verify(employeeRepository,times(1)).save(employee);
    }
    @Test
    public void findByIdThrowsException(){
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->employeeService.findById(1L));
    }

    @Test
    public void update(){
        var existingEmployee = new Employee();
        existingEmployee.setName("Name");
        existingEmployee.setEmail("email@test.com");
        existingEmployee.setCpf("123.456.789-00");
        existingEmployee.setBirthDate(LocalDate.of(1990, 1, 1));
        existingEmployee.setAcessLevel(AcessLevel.ATTENDANT);
        existingEmployee.setPassword("pass");
        existingEmployee.setOffice(Office.RECEPTIONIST);

        var employeeUpdateDto = new EmployeeCreateDto();
        employeeUpdateDto.setName("New Name");
        employeeUpdateDto.setEmail("email@test.com");
        employeeUpdateDto.setCpf("123.456.789-00");
        employeeUpdateDto.setBirthDate(LocalDate.of(1990, 1, 1));
        employeeUpdateDto.setAcessLevel(AcessLevel.MANAGER);
        employeeUpdateDto.setPassword("newPass");
        employeeUpdateDto.setOffice(Office.MANAGER);

        when(encoder.encode("newPass")).thenReturn("encodedNewPass");
        when(employeeRepository.findByEmail("email@test.com")).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(null);

        employeeService.update(employeeUpdateDto);

        assertEquals("encodedNewPass",existingEmployee.getPassword());
        assertEquals("New Name",existingEmployee.getName());
        assertEquals(AcessLevel.MANAGER,existingEmployee.getAcessLevel());
        assertEquals(Office.MANAGER,existingEmployee.getOffice());
        assertEquals(LocalDate.of(1990, 1, 1),existingEmployee.getBirthDate());
        assertEquals("123.456.789-00",employeeUpdateDto.getCpf());
        assertEquals("email@test.com",employeeUpdateDto.getEmail());
        verify(employeeRepository,times(1)).save(existingEmployee);


    }
}
