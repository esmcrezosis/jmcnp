package com.esmc.mcnp.domain.entity.obpsd;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "eu_approvisionnement")
public class EuApprovisionnement implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idApprovisionement;
    private String codeMembreApporteur;
    private String codeMembreBeneficiare;
    private Date dateApprovisionnement;
    private String typeApprovisionnement;
    private Double montantApprovisionnement;
    private Long idTpagcp;
    private Long idCanton;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_approvisionnement")
    public Long getIdApprovisionement() {
        return idApprovisionement;
    }

    public void setIdApprovisionement(Long idApprovisionement) {
        this.idApprovisionement = idApprovisionement;
    }

    @Column(name = "code_membre_apporteur")
    public String getCodeMembreApporteur() {
        return codeMembreApporteur;
    }

    public void setCodeMembreApporteur(String codeMembreApporteur) {
        this.codeMembreApporteur = codeMembreApporteur;
    }

    @Column(name = "code_membre_beneficiaire")
    public String getCodeMembreBeneficiare() {
        return codeMembreBeneficiare;
    }

    public void setCodeMembreBeneficiare(String codeMembreBeneficiare) {
        this.codeMembreBeneficiare = codeMembreBeneficiare;
    }

    @Column(name = "date_approvisionnement")
    public Date getDateApprovisionnement() {
        return dateApprovisionnement;
    }

    public void setDateApprovisionnement(Date dateApprovisionnement) {
        this.dateApprovisionnement = dateApprovisionnement;
    }

    @Column(name = "type_approvisionnement")
    public String getTypeApprovisionnement() {
        return typeApprovisionnement;
    }

    public void setTypeApprovisionnement(String typeApprovisionnement) {
        this.typeApprovisionnement = typeApprovisionnement;
    }

    @Column(name = "montant_approvisionnement")
    public Double getMontantApprovisionnement() {
        return montantApprovisionnement;
    }

    public void setMontantApprovisionnement(Double montantApprovisionnement) {
        this.montantApprovisionnement = montantApprovisionnement;
    }

    @Column(name = "id_canton")
    public Long getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(Long idCanton) {
        this.idCanton = idCanton;
    }

	/**
	 * @return the idTpagcp
	 */
    @Column(name = "id_tpagcp")
	public Long getIdTpagcp() {
		return idTpagcp;
	}

	/**
	 * @param idTpagcp the idTpagcp to set
	 */
	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}
}