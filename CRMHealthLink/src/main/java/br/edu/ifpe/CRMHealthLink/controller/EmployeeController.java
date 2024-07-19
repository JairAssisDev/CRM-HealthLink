package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.employeeDto.EmployeeResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.EmployeeMapper;
import br.edu.ifpe.CRMHealthLink.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.Employee;
import br.edu.ifpe.CRMHealthLink.service.EmployeeService;

import br.edu.ifpe.CRMHealthLink.service.PatientService;

import br.edu.ifpe.CRMHealthLink.entity.Office;

import lombok.RequiredArgsConstructor;
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
    private final PatientService patientService;




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

    @Operation(summary = "Adicionar um Gerente", description = "Adiciona um Gerente ao sistema")
    @PostMapping("/managers/{managerId}/add")
    public ResponseEntity<Void> addManager(@PathVariable Long managerId, @RequestBody EmployeeCreateDto newManagerDto) {
        Employee manager = employeeService.findById(managerId);
        if (manager == null || manager.getOffice() != Office.MANAGER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Employee newManager = EmployeeMapper.toEmployee(newManagerDto);
        newManager.setOffice(Office.MANAGER);
        employeeService.save(newManager);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Remover um Gerente", description = "Remove um Gerente do sistema")
    @DeleteMapping("/managers/{managerId}/remove/{employeeId}")
    public ResponseEntity<Void> removeManager(@PathVariable Long managerId, @PathVariable Long employeeId) {
        Employee manager = employeeService.findById(managerId);
        Employee employeeToRemove = employeeService.findById(employeeId);
        if (manager == null || employeeToRemove == null || manager.getOffice() != Office.MANAGER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        employeeService.delete(employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /*Falta a iplementação de token*/
    @Operation(summary = "Adicionar um Atendente", description = "Adiciona um Atendente ao sistema")
    @PostMapping("/managers/{managerId}/attendants")
    public ResponseEntity<Void> addAttendant(@PathVariable Long managerId, @RequestBody EmployeeCreateDto newAttendantDto) {
        Employee manager = employeeService.findById(managerId);
        if (manager == null || manager.getOffice() != Office.MANAGER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Employee newAttendant = EmployeeMapper.toEmployee(newAttendantDto);
        newAttendant.setOffice(Office.RECEPTIONIST);
        employeeService.save(newAttendant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /*Falta a iplementação de token*/
    @Operation(summary = "Remover um Atendente", description = "Remove um Atendente do sistema")
    @DeleteMapping("/managers/{managerId}/attendants/{attendantId}")
    public ResponseEntity<Void> removeAttendant(@PathVariable Long managerId, @PathVariable Long attendantId) {
        Employee manager = employeeService.findById(managerId);
        Employee attendantToRemove = employeeService.findById(attendantId);
        if (manager == null || attendantToRemove == null || manager.getOffice() != Office.MANAGER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        employeeService.delete(attendantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*Possivel modificação*/
    @Operation(summary = "Cadastrar um paciente", description = "Cadastra um paciente no sistema")
    @PostMapping("/attendants/{attendantId}/patients")
    public ResponseEntity<Void> registerPatient(@PathVariable Long attendantId, @RequestBody PatientCreateDto patientDto) {
        Employee attendant = employeeService.findById(attendantId);
        if (attendant == null || attendant.getOffice() != Office.RECEPTIONIST) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        patientService.save(PatientMapper.toPatient(patientDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /*Possivel modificação*/
    @Operation(summary = "Remover um paciente", description = "Remove um paciente do sistema")
    @DeleteMapping("/attendants/{attendantId}/patients/{patientId}")
    public ResponseEntity<Void> removePatient(@PathVariable Long attendantId, @PathVariable Long patientId) {
        Employee attendant = employeeService.findById(attendantId);
        if (attendant == null || attendant.getOffice() != Office.RECEPTIONIST) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        patientService.delete(patientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
