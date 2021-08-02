package com.esmc.mcnp.web.model.obps;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ChaineDistributionForm {
    private Integer id;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private String typeChaine;
    private Boolean autonome;
    private Integer idChaineParent;
    private String codeMembreMorale;
    private Boolean valider;
    private Integer idCanton;
}
