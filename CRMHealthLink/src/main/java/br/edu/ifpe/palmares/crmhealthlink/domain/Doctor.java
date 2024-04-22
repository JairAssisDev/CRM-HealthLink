package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="doctor")
@PrimaryKeyJoinColumn(name = "id")
public class Doctor extends Users {




}
