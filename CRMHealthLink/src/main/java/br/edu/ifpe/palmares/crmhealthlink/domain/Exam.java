package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="exam")
public class Exam extends HealthService{

}
