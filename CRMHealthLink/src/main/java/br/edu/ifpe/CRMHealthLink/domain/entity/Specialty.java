package br.edu.ifpe.CRMHealthLink.domain.entity;

public enum Specialty {
    CLINICOGERAL(0),
    PEDIATRA(1),
    UROLOGISTA(2),
    PROCTOLOGISTA(3),
    CARDIOLOGISTA(4),
    NEUROLOGISTA(5),
    ORTOPEDISTA(6),
    DERMATOLOGISTA(7),
    GINECOLOGISTA(8),
    ENDCRINOLOGISTA(9),
    PSIQUIATRA(10),
    OFTALMOLOGISTA(11),
    OTORRINOLARINGOLOGISTA(12),
    ONCOLOGISTA(13),
    GASTROENTEROLOGISTA(14);
    private final Integer specialty;
    Specialty(Integer specialty) {
        this.specialty = specialty;
    }
}
