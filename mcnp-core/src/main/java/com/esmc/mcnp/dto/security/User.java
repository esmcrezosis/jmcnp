package com.esmc.mcnp.dto.security;

import com.esmc.mcnp.dto.odd.AgencesOdd;
import com.esmc.mcnp.dto.odd.Centres;
import com.esmc.mcnp.dto.org.AgenceDTO;
import com.esmc.mcnp.dto.vue.AgencesOddDTO;
import com.esmc.mcnp.dto.vue.CentreDTO;
import com.esmc.mcnp.dto.vue.RoleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private Long idUtilisateur;
    private String codeMembre;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String login;
    private String pwd;
    private String passwordHash;
    private Integer chPwdFlog;
    private int ulock;
    private String questionSecrete;
    private String reponse;
    private Long idUtilisateurParent;
    private String codeActeur;
    private String codeGacFiliere;
    private String codeGroupeCreate;
    private String codeTegc;
    private String codePasse;
    private String codeZone;
    private Integer connecte;
    private String description;
    private Integer idFiliere;
    private Integer idPays;
    private String role;
    private String section;
    private String odd;
    private String cible;
    private String indicateur;
    private UserGroupDTO userGroup;
    private CentreDTO centre;
    private AgencesOddDTO agencesOdd;
    private AgenceDTO agence;
    private List<RoleDTO> roles;
}
