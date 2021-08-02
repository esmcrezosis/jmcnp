package com.esmc.mcnp.domain.entity.enums;

public enum NiveauValidation {
    CANTON("canton"),
    PREFECTURE("prefecture"),
    REGION("region"),
    PAYS("pays"),
    ZONE("zone"),
    MONDE("monde");

    private String niveau;

    NiveauValidation(String niveau) {
        this.niveau = niveau;
    }

    public String getNiveau() {
        return this.niveau;
    }
}
