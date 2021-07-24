package com.esmc.mcnp.model.odd;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: EuMembreFifo
 *
 */
@Entity
@Table(name = "eu_membre_fifo")
public class EuMembreFifo implements Serializable {

	
	private Long idMembreFifo;
	private String codeMembreBenef;
	private String motifDesactivation;
	private Integer substituer;
	private String motifSubstitution;
	private String codeMembreSubstituer;
	private Long utilisateur;
	private Integer desactiver;
	private Integer valider;
	private Integer affecter;
	private static final long serialVersionUID = 1L;

	public EuMembreFifo() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_membre_fifo")
	public Long getIdMembreFifo() {
		return this.idMembreFifo;
	}

	public void setIdMembreFifo(Long idMembreFifo) {
		this.idMembreFifo = idMembreFifo;
	}   
	
	@Column(name = "code_membre_benef")
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	} 
	
	public Integer getDesactiver() {
		return this.desactiver;
	}

	public void setDesactiver(Integer desactiver) {
		this.desactiver = desactiver;
	} 
	
	@Column(name = "motif_desactivation")
	public String getMotifDesactivation() {
		return this.motifDesactivation;
	}

	public void setMotifDesactivation(String motifDesactivation) {
		this.motifDesactivation = motifDesactivation;
	}
	
	public Integer getSubstituer() {
		return this.substituer;
	}
	
	public void setSubstituer(Integer substituer) {
		this.substituer = substituer;
	}
	
	@Column(name = "motif_substitution")
	public String getMotifSubstitution() {
		return this.motifSubstitution;
	}

	public void setMotifSubstitution(String motifSubstitution) {
		this.motifSubstitution = motifSubstitution;
	}
	
	@Column(name = "code_membre_substituer")
	public String getCodeMembreSubstituer() {
		return this.codeMembreSubstituer;
	}

	public void setCodeMembreSubstituer(String codeMembreSubstituer) {
		this.codeMembreSubstituer = codeMembreSubstituer;
	}   
	public Long getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Long utilisateur) {
		this.utilisateur = utilisateur;
	}   
	public Integer getValider() {
		return this.valider;
	}

	public void setValider(Integer valider) {
		this.valider = valider;
	}   
	public Integer getAffecter() {
		return this.affecter;
	}

	public void setAffecter(Integer affecter) {
		this.affecter = affecter;
	}
   
}
