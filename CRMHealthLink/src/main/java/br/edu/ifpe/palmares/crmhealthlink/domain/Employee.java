package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="employee")
@PrimaryKeyJoinColumn(name = "id")
@Data
public class Employee extends Users {


}
