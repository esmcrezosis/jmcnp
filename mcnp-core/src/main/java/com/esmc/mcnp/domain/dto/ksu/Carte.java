package com.esmc.mcnp.dto.ksu;

import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Carte implements Serializable {
    private Long idCarte;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDemande;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateImpression;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateLivraison;
    private Boolean livrer;
    private Boolean imprimer;
    private String urlCarte;
    private String codeMembre;
    private String codeMembreMorale;
    private String userType;
    private Long idUtilisateur;
}
