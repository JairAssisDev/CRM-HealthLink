package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "id")
public class Patient extends Users {


    public String test(){
        return this.name;
    }

}
