package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.domain.useCase.IEmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/employee")
@Tag(name = "Employee API", description = "API para gestão de funcionários")
public class EmployeeController {

    private final IEmployeeService employeeService;


}
