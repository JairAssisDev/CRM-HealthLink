package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.employeeDto.EmployeeResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.EmployeeMapper;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.Employee;
import br.edu.ifpe.CRMHealthLink.service.EmployeeService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/employee")
@Tag(name = "Employee API", description = "API para gestão de funcionários")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;

    @PostMapping("create/patient")
    public ResponseEntity createPatient(@RequestBody @Valid PatientCreateDto patient){
        if(userService.getUserByEmail(patient.getEmail()) != null){
            return ResponseEntity.badRequest().body("User already exists!");
        }
        patient.setAcessLevel(AcessLevel.PATIENT);
        patientService.save(patient);
        return ResponseEntity.ok().build();
    }






    @Operation(summary = "Cria um novo funcionário", description = "Cria um novo funcionário com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> create(@RequestBody EmployeeCreateDto employee) {
        Employee responseEmployee = employeeService.save(EmployeeMapper.toEmployee(employee));
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeMapper.toDtoEmployee(responseEmployee));
    }

    @Operation(summary = "Obtém todos os funcionários", description = "Obtém a lista de todos os funcionários")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> findAll() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(EmployeeMapper.toDtoEmployees(employees));
    }

    @Operation(summary = "Obtém um funcionário pelo ID", description = "Obtém os detalhes de um funcionário pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(EmployeeMapper.toDtoEmployee(employee));
    }

    @Operation(summary = "Remove um funcionário pelo ID", description = "Remove um funcionário pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            employeeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualiza um funcionário pelo ID", description = "Atualiza os dados de um funcionário pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeCreateDto employee) {
        employeeService.update(id, employee);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
