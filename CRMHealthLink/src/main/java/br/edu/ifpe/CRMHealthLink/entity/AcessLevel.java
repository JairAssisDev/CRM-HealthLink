package br.edu.ifpe.CRMHealthLink.entity;

public enum AcessLevel {
    PATIENT(1),DOCTOR(2),ATTENDANT(3),MANAGER(4);
    public final Integer level;
    AcessLevel(Integer level){
        this.level = level;
    }
}
