package com.esmc.mcnp.domain.enums;

public enum ChaineDistributionEnum {

    ENTREPOT("entrepot"), MARCHE("marche"), MAGASIN("magasin"), BOUTIQUE("boutique");

    private String chaine;

    ChaineDistributionEnum(String chaine) {
        this.chaine = chaine;
    }

    public String getChaine() {
        return chaine;
    }
}
