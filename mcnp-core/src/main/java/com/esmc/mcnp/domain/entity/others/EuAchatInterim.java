package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

/**
 * The persistent class for the eu_achat_interim database table.
 *
 */
@Entity
@Table(name = "eu_achat_interim")
@NamedQuery(name = "EuAchatInterim.findAll", query = "SELECT e FROM EuAchatInterim e")
public class EuAchatInterim implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAchat;
    private String codeAchat;
    private Double montantAchat;
    private String nomAcheteur;
    private String prenomAcheteur;
    private String raisonAcheteur;
    private Date dateAchat;
    private String codeMembre;
    private EuUtilisateur euUtilisateur;
    private EuBon euBon;
    private Integer Status;
    private String codeBan;
    
    public EuAchatInterim() {
    }

    @Id
    @Column(name = "id_achat", unique = true, nullable = false)
    public Long getIdAchat() {
		return idAchat;
	}

	public void setIdAchat(Long idAchat) {
		this.idAchat = idAchat;
	}
	
	@Column(name = "code_achat")
	public String getCodeAchat() {
		return codeAchat;
	}

	public void setCodeAchat(String codeAchat) {
		this.codeAchat = codeAchat;
	}
	
	@Column(name = "montant_achat")			
	public Double getMontantAchat() {
		return montantAchat;
	}

	public void setMontantAchat(Double montantAchat) {
		this.montantAchat = montantAchat;
	}

	@Column(name = "nom_acheteur")			
	public String getNomAcheteur() {
		return nomAcheteur;
	}

	public void setNomAcheteur(String nomAcheteur) {
		this.nomAcheteur = nomAcheteur;
	}
	@Column(name = "prenom_acheteur")
	public String getPrenomAcheteur() {
		return prenomAcheteur;
	}

	public void setPrenomAcheteur(String prenomAcheteur) {
		this.prenomAcheteur = prenomAcheteur;
	}
	@Column(name = "raison_acheteur")
	public String getRaisonAcheteur() {
		return raisonAcheteur;
	}

	public void setRaisonAcheteur(String raisonAcheteur) {
		this.raisonAcheteur = raisonAcheteur;
	}
	
	@Temporal(TemporalType.DATE)
    @Column(name = "date_achat")
	public Date getDateAchat() {
		return dateAchat;
	}

	public void setDateAchat(Date dateAchat) {
		this.dateAchat = dateAchat;
	}
	
	@Column(name = "code_membre")
	public String getCodeMembre() {
		return codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "bon_id")
	public EuBon getEuBon() {
		return euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}
	
	@Column(name = "code_ban")
	public String getCodeBan() {
		return codeBan;
	}

	public void setCodeBan(String codeBan) {
		this.codeBan = codeBan;
	}

   
    
}
