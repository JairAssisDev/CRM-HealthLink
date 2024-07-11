package br.edu.ifpe.CRMHealthLink.entity;

public enum AcessLevel {
    PATIENT(0),DOCTOR(1),ATTENDANT(2),MANGER(3);
    public final Integer level;
    AcessLevel(Integer level){
        this.level = level;
    }
}
