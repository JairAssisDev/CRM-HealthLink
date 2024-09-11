package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IEmployeeService;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IPatientService;
import br.edu.ifpe.CRMHealthLink.service.DoctorServiceImpl;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import br.edu.ifpe.CRMHealthLink.service.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.service.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.employeeDto.EmployeeResponseDto;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.DoctorMapper;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.EmployeeMapper;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.service.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.patientDto.PatientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/employee")
@Tag(name = "Employee API", description = "API para gestão de funcionários")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @Autowired
    private UserService userService;
    @Autowired
    private IPatientService patientService;



    @Operation(summary = "Cria um novo Paciente",description = "Cria um novo  médico  com base nas informações fornecidas")
    @PostMapping("create/patient")
    public ResponseEntity createPatient(@RequestBody @Valid PatientCreateDto patient){
        if(userExists(patient)){
            return ResponseEntity.badRequest().body("User already exists!");
        }
        patient.setAcessLevel(AcessLevel.PATIENT);
        patientService.save(null);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtém todos os Pacientes",description = "Obtém a lista de todos os Pacientes")
    @GetMapping("/pacientes")
    public ResponseEntity<List<PatientResponseDto>> findAllPacientes() {
        return ResponseEntity.ok(PatientMapper.toDtoPacients(patientService.getAll()));
    }

    @Operation(summary = "Obtém um Paciente pelo ID", description = "Obtém os detalhes de um Paciente pelo seu ID")
    @GetMapping("/getpaciente/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        if(patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(PatientMapper.toDtoPatient(patient));
    }

    @Operation(summary = "Remove um Paciente pelo ID",description = "Remove um Paciente pelo seu ID")
    @DeleteMapping("/paciente/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        try {
            patientService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualiza um Paciente", description = "Atualiza o Paciente com base nas novas informações fornecidas ")
    @PutMapping("/paciente/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody PatientCreateDto patientCreateDto){
        patientService.update(id,null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




    //employee

    @PostMapping("create/employee")
    public ResponseEntity createEmployee(@RequestBody @Valid EmployeeCreateDto employee){
        employee.setAcessLevel(AcessLevel.ATTENDANT);

        if(userExists(employee)){
            return ResponseEntity.badRequest().body("User already exists!");
        }

        employeeService.save(null);
        return ResponseEntity.ok().build();
    }
    public boolean userExists(UserCreateDto user){
        return userService.getUserByEmail(user.getEmail()) != null;
    }




/*
    @Operation(summary = "Cria um novo funcionário", description = "Cria um novo funcionário com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> create(@RequestBody EmployeeCreateDto employee) {
        Employee responseEmployee = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeMapper.toDtoEmployee(responseEmployee));
    }
*/
    @Operation(summary = "Obtém todos os funcionários", description = "Obtém a lista de todos os funcionários")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> findAll() {
        List<Employee> employees = employeeService.getAll();
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
/*
    @Operation(summary = "Remove um funcionário pelo ID", description = "Remove um funcionário pelo seu ID")
    @DeleteMapping("/{employeeid}/{managerid}")
    public ResponseEntity<Void> delete(@PathVariable Long employeeid ,Long managerid) {
        Employee employeeManager = employeeService.findById(managerid);
        if (employeeManager.getAcessLevel() == AcessLevel.MANAGER) {
            try {
                employeeService.delete(employeeid);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


 */
    @Operation(summary = "Atualiza um funcionário pelo ID", description = "Atualiza os dados de um funcionário pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeCreateDto employee) {
        employeeService.update(id, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //medico
    @Autowired
    private final DoctorServiceImpl doctorServiceImpl;

    @Operation(summary = "Cria um novo médico", description = "Cria um novo médico com base nas informações fornecidas")
    @PostMapping("/doctor/{managerid}")
    public ResponseEntity<Doctor> createDoctor(@RequestBody DoctorCreateDto doctor, @PathVariable Long managerid) {
        Employee employee = employeeService.findById(managerid);
        if (employee.getAcessLevel()==AcessLevel.MANAGER) {
            Doctor responseDoctor = doctorServiceImpl.save(DoctorMapper.toDoctorEntity(doctor));
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDoctor);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Obtém todos os médicos", description = "Obtém a lista de todos os médicos")
    @GetMapping("/doctors")
    private ResponseEntity<List<DoctorResponseDto>> findAllDoctors() {
        List<Doctor> doctors = doctorServiceImpl.getAllDoctors();
        return ResponseEntity.ok(DoctorMapper.toDtoDoctors(doctors));
    }
    @Operation(summary = "Obtém um médico pelo ID", description = "Obtém os detalhes de um médico pelo seu ID")
    @GetMapping("/doctor/{id}")
    private ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorServiceImpl.findById(id);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(DoctorMapper.toDtoDoctor(doctor));
    }

    @Operation(summary = "Atualiza um médico pelo ID", description = "Atualiza os dados de um médico pelo seu ID")
    @PutMapping("/doctor/{id}")
    public ResponseEntity<Void> updateDoctor(@PathVariable Long id, @RequestBody DoctorCreateDto doctor) {
        doctorServiceImpl.update(id, doctor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
