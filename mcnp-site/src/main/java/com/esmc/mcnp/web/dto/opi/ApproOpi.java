package com.esmc.mcnp.web.dto.opi;

import java.util.List;

public class ApproOpi {
	private String codeMembreAcheteur;
	private String codeMembreVendeur;
	private Long idTpagcp;
	private Double montant;
	private String moyenPayement;
	private String numeroCompte;
	private Integer nbreOpi;
	private List<Long> traites;

	public ApproOpi() {
	}

	public ApproOpi(String codeMembreAcheteur, String codeMembreVendeur, Double montant, String moyenPayement,
			String numeroCompte, Long idTpagcp, Integer nbreOpi, List<Long> traites) {
		this.codeMembreAcheteur = codeMembreAcheteur;
		this.codeMembreVendeur = codeMembreVendeur;
		this.montant = montant;
		this.moyenPayement = moyenPayement;
		this.numeroCompte = numeroCompte;
		this.idTpagcp = idTpagcp;
		this.nbreOpi = nbreOpi;
		this.traites = traites;
	}

	public String getCodeMembreAcheteur() {
		return codeMembreAcheteur;
	}

	public void setCodeMembreAcheteur(String codeMembreAcheteur) {
		this.codeMembreAcheteur = codeMembreAcheteur;
	}

	public String getCodeMembreVendeur() {
		return codeMembreVendeur;
	}

	public void setCodeMembreVendeur(String codeMembreVendeur) {
		this.codeMembreVendeur = codeMembreVendeur;
	}

	public String getMoyenPayement() {
		return moyenPayement;
	}

	public void setMoyenPayement(String moyenPayement) {
		this.moyenPayement = moyenPayement;
	}

	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Long getIdTpagcp() {
		return idTpagcp;
	}

	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}

	public Integer getNbreOpi() {
		return nbreOpi;
	}

	public void setNbreOpi(Integer nbreOpi) {
		this.nbreOpi = nbreOpi;
	}

	public List<Long> getTraites() {
		return traites;
	}

	public void setTraites(List<Long> traites) {
		this.traites = traites;
	}
}
