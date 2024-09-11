package br.edu.ifpe.CRMHealthLink.entity;

public enum AcessLevel {
    PATIENT("PATIENT"),DOCTOR("DOCTOR"),ATTENDANT("ATTENDANT"),MANAGER("MANAGER");
    public final String level;
    AcessLevel(String level){
        this.level = level;
    }
}
