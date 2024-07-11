package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor extends User{

    @Column
    String CRM;
    @Column
    String Specialty;
}
