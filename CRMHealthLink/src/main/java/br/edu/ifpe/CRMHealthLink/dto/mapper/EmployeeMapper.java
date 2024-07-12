package br.edu.ifpe.CRMHealthLink.dto.mapper;

import br.edu.ifpe.CRMHealthLink.dto.EmployeeResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.Employee;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

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

