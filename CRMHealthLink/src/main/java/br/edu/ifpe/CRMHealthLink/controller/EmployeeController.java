package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.DoctorMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.EmployeeMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.*;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import br.edu.ifpe.CRMHealthLink.service.EmployeeService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Arrays;
import java.util.List;



@RestController
@RequestMapping("api/employee")
@Tag(name = "Employee API", description = "API para gestão de funcionários")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final UserService userService;

    private final PatientService patientService;

    private final DoctorService doctorService;

    public EmployeeController(EmployeeService employeeService, UserService userService, PatientService patientService, DoctorService doctorService) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }
    // pacentes

    @Operation(summary = "Cria um novo Paciente",description = "Cria um novo  médico  com base nas informações fornecidas")
    @PostMapping("create/patient")
    public ResponseEntity<String> createPatient(@RequestBody @Valid PatientCreateDto patientDTO){
        if(userExists(patientDTO)){
            return ResponseEntity.badRequest().body("User already exists!");
        }
        Patient patient = PatientMapper.toPatient(patientDTO);
        patientService.save(patient);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtém todos os Pacientes",description = "Obtém a lista de todos os Pacientes")
    @GetMapping("/pacientes")
    public ResponseEntity<List<PatientResponseDto>> findAllPacientes() {
        return ResponseEntity.ok(PatientMapper.toDtoPacients(patientService.getAllPatient()));
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
    public ResponseEntity<Void> deletePacinet(@PathVariable Long id) {
        try {
            patientService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualiza um Paciente", description = "Atualiza o Paciente com base nas novas informações fornecidas ")
    @PutMapping("/paciente/{id}")
    public ResponseEntity<Void> updatePatient(@PathVariable Long id, @RequestBody PatientCreateDto patientCreateDto){
        Patient patient = PatientMapper.toPatient(patientCreateDto);
        patientService.update(id,patient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //employee

    @PostMapping("create/employee")
    public ResponseEntity<String> createEmployee(@RequestBody @Valid EmployeeCreateDto employeeDTO){
        employeeDTO.setAcessLevel(AcessLevel.ATTENDANT);

        if(userExists(employeeDTO)){
            return ResponseEntity.badRequest().body("User already exists!");
        }
        Employee employee = EmployeeMapper.toEmployee(employeeDTO);
        employeeService.save(employee);
        return ResponseEntity.ok().build();
    }
    public boolean userExists(UserCreateDto user){
        return userService.getUserByEmail(user.getEmail()) != null;
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

    @Operation(summary = "Atualiza um funcionário pelo ID", description = "Atualiza os dados de um funcionário pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeCreateDto employee) {
        employeeService.update(id, employee);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Cria um novo médico", description = "Cria um novo médico com base nas informações fornecidas")
    @PostMapping("/doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody DoctorCreateDto doctor) {

        var loggedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedUser.getAcessLevel()==AcessLevel.MANAGER) {
            Doctor responseDoctor = doctorService.save(DoctorMapper.toDoctorEntity(doctor));
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDoctor);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Obtém todos os médicos", description = "Obtém a lista de todos os médicos")
    @GetMapping("/doctors")
    private ResponseEntity<List<DoctorResponseDto>> findAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(DoctorMapper.toDtoDoctors(doctors));
    }
    @Operation(summary = "Obtém um médico pelo ID", description = "Obtém os detalhes de um médico pelo seu ID")
    @GetMapping("/doctor/{id}")
    private ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.findById(id);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(DoctorMapper.toDtoDoctor(doctor));
    }

    @Operation(summary = "Atualiza um médico pelo ID", description = "Atualiza os dados de um médico pelo seu ID")
    @PutMapping("/doctor/{id}")
    public ResponseEntity<Void> updateDoctor(@PathVariable Long id, @RequestBody DoctorCreateDto doctor) {
        doctorService.update(id, doctor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Lista todos os medicos por especialidade",description = "Lista todos os medicos por especialidade")
    @GetMapping("/doctors/specialty")
    private ResponseEntity<List<DoctorResponseDto>> findAllSpecialties(Speciality speciality) {
        List<Doctor> doctors = doctorService.findAllDoctorBySpecialty(speciality);
        List<DoctorResponseDto> doctorResponseDtos = DoctorMapper.toDtoDoctors(doctors);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseDtos);
    }
    @Operation(summary = "Lista todas especialindades", description = "Lista todas especialindades")
    @GetMapping("allspecialities")
    public List<Speciality> getAllSpecialities() {

        return Arrays.asList(Speciality.values());
    }


}
