package br.edu.ifpe.CRMHealthLink.entity;

public enum AccessLevel {
    PATIENT("PATIENT"),DOCTOR("DOCTOR"),ATTENDANT("ATTENDANT"),MANAGER("MANAGER");
    public final String level;
    AccessLevel(String level){
        this.level = level;
    }
}
