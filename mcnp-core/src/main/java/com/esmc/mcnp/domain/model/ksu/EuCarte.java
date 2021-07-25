/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.model.ksu;

import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "eu_carte")
public class EuCarte implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idCarte;
    private LocalDate dateDemande;
    private LocalDate dateImpression;
    private LocalDate dateLivraison;
    private boolean livrer;
    private boolean imprimer;
    private Long idUtilisateur;
    private String urlCarte;
    private EuMembre euMembre;
    private EuMembreMorale euMembreMorale;
    private String userType;

    public EuCarte() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carte")
    public Long getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(Long idCarte) {
        this.idCarte = idCarte;
    }

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_demande")
    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_impression")
    public LocalDate getDateImpression() {
        return dateImpression;
    }

    public void setDateImpression(LocalDate dateImpression) {
        this.dateImpression = dateImpression;
    }

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_livraison")
    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public boolean isLivrer() {
        return livrer;
    }

    public void setLivrer(boolean livrer) {
        this.livrer = livrer;
    }

    public boolean isImprimer() {
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "url_carte")
    public String getUrlCarte() {
        return urlCarte;
    }

    public void setUrlCarte(String urlCarte) {
        this.urlCarte = urlCarte;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembre getEuMembre() {
        return euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

	@Column(name = "user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
