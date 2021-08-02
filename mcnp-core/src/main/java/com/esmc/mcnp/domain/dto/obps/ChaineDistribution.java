package com.esmc.mcnp.domain.dto.obps;

import java.time.LocalDate;

import com.esmc.mcnp.domain.dto.cm.MembreMorale;
import com.esmc.mcnp.domain.dto.odd.AgencesOdd;
import com.esmc.mcnp.domain.enums.ChaineDistributionEnum;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ChaineDistribution {
    private Integer id;
    private LocalDate dateCreate;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private ChaineDistributionEnum typeChaine;
    private Boolean autonome;
    private Boolean valider;
    private MembreMorale proprio;
    private AgencesOdd agencesOdd;
}
