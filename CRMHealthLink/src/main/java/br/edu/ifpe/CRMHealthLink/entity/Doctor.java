package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor")
@Getter
@Setter
public class Doctor extends User {

    @Column
    private String CRM;

    @Column
    private String Specialty;
}
