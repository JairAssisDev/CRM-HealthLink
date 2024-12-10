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

    @Operation(summary = "Obtém um Paciente pelo email", description = "Obtém os detalhes de um Paciente pelo seu email")
    @GetMapping("/paciente/{email}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable String email) {
        Patient patient = patientService.getByEmail(email).orElse(null);
        if(patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(PatientMapper.toDtoPatient(patient));
    }

    @Operation(summary = "Remove um Paciente pelo email",description = "Remove um Paciente pelo seu email")
    @DeleteMapping("/paciente/{email}")
    public ResponseEntity<Void> deletePacinet(@PathVariable String email) {
        patientService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualiza um Paciente", description = "Atualiza o Paciente com base nas novas informações fornecidas ")
    @PutMapping("/paciente")
    public ResponseEntity<Void> updatePatient(@RequestBody PatientCreateDto patientCreateDto){
        Patient patient = PatientMapper.toPatient(patientCreateDto);
        patientService.update(patient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //employee

    @PostMapping("create/employee")
    public ResponseEntity<String> createEmployee(@RequestBody @Valid EmployeeCreateDto employeeDTO){
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

    @Operation(summary = "Obtém um funcionário pelo email", description = "Obtém os detalhes de um funcionário pelo seu email")
    @GetMapping("/{email}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable String email) {
        Employee employee = employeeService.findByEmail(email).orElse(null);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(EmployeeMapper.toDtoEmployee(employee));
    }

    @Operation(summary = "Atualiza um funcionário ", description = "Atualiza os dados de um funcionário")
    @PutMapping()
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeCreateDto employee) {
        employeeService.update(employee);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Cria um novo médico", description = "Cria um novo médico com base nas informações fornecidas")
    @PostMapping("/doctor")
    public ResponseEntity<DoctorResponseDto> createDoctor(@RequestBody DoctorCreateDto doctor) {

        var loggedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedUser.getAcessLevel()==AcessLevel.MANAGER) {
            Doctor d = DoctorMapper.toDoctorEntity(doctor);
            d.setAcessLevel(AcessLevel.DOCTOR); //Não estava salvando com o acess level de doctor
            Doctor responseDoctor = doctorService.save(d);
            return ResponseEntity.status(HttpStatus.CREATED).body(DoctorMapper.toDtoDoctor(responseDoctor));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Obtém todos os médicos", description = "Obtém a lista de todos os médicos")
    @GetMapping("/doctors")
    private ResponseEntity<List<DoctorResponseDto>> findAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(DoctorMapper.toDtoDoctors(doctors));
    }
    @Operation(summary = "Obtém um médico pelo email", description = "Obtém os detalhes de um médico pelo seu email")
    @GetMapping("/doctor/{email}")
    private ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable String email) {
        Doctor doctor = doctorService.getByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(DoctorMapper.toDtoDoctor(doctor));
    }

    @Operation(summary = "Atualiza um médico", description = "Atualiza os dados de um médico")
    @PutMapping("/doctor")
    public ResponseEntity<Void> updateDoctor(@RequestBody DoctorCreateDto doctor) {
        doctorService.update(doctor);
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

    @Operation(summary = "Lista dos Tipo Agendamentos", description = "Lista dos Tipo Agendamentos")
    @GetMapping("alltipoagendamentos")
    public List<TipoAgendamento> getAllTipoAgendamentos() {

        return Arrays.asList(TipoAgendamento.values());
    }

}
