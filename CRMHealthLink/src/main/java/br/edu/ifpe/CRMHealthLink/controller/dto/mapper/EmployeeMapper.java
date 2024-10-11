package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EmployeeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Employee toEmployee(EmployeeCreateDto createDto) {
        return modelMapper.map(createDto, Employee.class);
    }

    public static EmployeeResponseDto toDtoEmployee(Employee employee) {
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }

    public static List<EmployeeResponseDto> toDtoEmployees(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeMapper::toDtoEmployee)
                .collect(Collectors.toList());
    }
}

