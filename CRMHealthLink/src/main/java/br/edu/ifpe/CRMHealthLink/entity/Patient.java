package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient extends User{
}
