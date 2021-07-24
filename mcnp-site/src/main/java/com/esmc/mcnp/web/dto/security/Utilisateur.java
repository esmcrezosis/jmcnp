package com.esmc.mcnp.web.dto.security;

import com.esmc.mcnp.model.org.EuAgence;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.odd.EuCentres;
import com.esmc.mcnp.model.security.EuUserGroup;

@lombok.Data
public class Utilisateur {

	private Long idUtilisateur;
	private Integer chPwdFlog;
	private String codeActeur;
	private String codeGacFiliere;
	private String codeGroupeCreate;
	private String codeMembre;
	private String codePasse;
	private String codeZone;
	private Integer connecte;
	private String description;
	private Integer idFiliere;
	private Integer idPays;
	private Long idUtilisateurParent;
	private String login;
	private String nomUtilisateur;
	private String prenomUtilisateur;
	private String pwd;
	private String questionSecrete;
	private String reponse;
	private int ulock;
	private String codeTegc;
	private String role;
	private String section;
	private String odd;
	private String cible;
	private String indicateur;
	private EuCentres centre;
	private EuAgencesOdd agencesOdd;
	private EuUserGroup euUserGroup;
	private EuAgence euAgence;

}
