package com.esmc.mcnp.domain.dto.cm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import com.esmc.mcnp.domain.dto.odd.AgencesOdd;
import com.esmc.mcnp.domain.dto.org.AgenceDTO;
import com.esmc.mcnp.domain.dto.org.Canton;
import com.esmc.mcnp.domain.dto.org.Pays;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Membre implements Serializable {
    private String codeMembre;
    private String nomMembre;
    private String prenomMembre;
    private Date dateNaisMembre;
    private String lieuNaisMembre;
    private String sexeMembre;
    private String sitfamMembre;
    private Integer nbrEnfMembre;
    private String bpMembre;
    private String quartierMembre;
    private String portableMembre;
    private String telMembre;
    private String villeMembre;
    private String emailMembre;
    private String formation;
    private String professionMembre;
    private String pereMembre;
    private String mereMembre;
    private String autoEnroler;
    private String etatMembre;
    private Integer desactiver;
    private String codesecret;
    private String codeGac;
    private Long idMaison;
    private Long idUtilisateur;
    private String mifarecard;
    private Canton canton;
    private Religion religion;
    private Pays pays;
    private AgenceDTO agence;
    private AgencesOdd agencesOdd;
    private LocalTime heureIdentification;
    private Date dateIdentification;
}
