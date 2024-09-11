package br.edu.ifpe.CRMHealthLink.domain.entity;

public enum Office {
    RECEPTIONIST(0),MANAGER(1);
    public final Integer office;
    Office(Integer office) {
        this.office = office;
    }
}
