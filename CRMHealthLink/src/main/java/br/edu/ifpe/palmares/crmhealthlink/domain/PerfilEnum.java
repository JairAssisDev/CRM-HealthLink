package br.edu.ifpe.palmares.crmhealthlink.domain;

public enum PerfilEnum {
    ATTENDANT(1),MANAGER(2),DOCTOR(3);
    private int cod;
    PerfilEnum(int cod) {
        this.cod = cod;
    }
    public int getCod() {
        return cod;
    }
}
