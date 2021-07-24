package com.esmc.mcnp.web.model.enums;

public enum TypeCentreEnum {
    CANTON("canton"),
    PREFECTURE("prefecture"),
    REGION("region"),
    PAYS("pays"),
    ZONE("zone monetaire"),
    MONDE("monde");

    private final String typeCentre;

    TypeCentreEnum(String typeCentre) {
        this.typeCentre = typeCentre;
    }

    public String getTypeCentre() {
        return typeCentre;
    }
}