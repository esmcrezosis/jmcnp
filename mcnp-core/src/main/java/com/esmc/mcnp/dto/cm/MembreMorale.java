package com.esmc.mcnp.dto.cm;

import com.esmc.mcnp.dto.odd.AgencesOdd;
import com.esmc.mcnp.dto.org.Pays;
import com.esmc.mcnp.dto.org.AgenceDTO;
import com.esmc.mcnp.dto.org.Canton;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MembreMorale implements Serializable {
    private String codeMembreMorale;
    private String raisonSociale;
    private String numRegistreMembre;
    private String bpMembre;
    private String telMembre;
    private String portableMembre;
    private String quartierMembre;
    private String villeMembre;
    private String emailMembre;
    private String siteWeb;
    private String domaineActivite;
    private Long idFiliere;
    private String typeFournisseur;
    private String codeGacFiliere;
    private String codeTypeActeur;
    private Integer desactiver;
    private String etatMembre;
    private String autoEnroler;
    private String codesecret;
    private Date dateIdentification;
    private LocalTime heureIdentification;
    private Long idUtilisateur;
    private Pays pays;
    private StatutJuridique statutJuridique;
    private AgenceDTO agence;
    private Canton canton;
    private AgencesOdd agencesOdd;

    public MembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }
}
