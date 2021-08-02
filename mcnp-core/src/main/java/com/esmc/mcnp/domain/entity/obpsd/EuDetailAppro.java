package com.esmc.mcnp.domain.entity.obpsd;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "eu_detail_approvisionnement")
public class EuDetailAppro implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDetailAppro;
    private EuApprovisionnement approvisionnement;
    private String codeCompte;
    private String codeCapa;
    private Long idCredit;
    private Long idTraite;
    private Double montantDetailAppro;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail_approvisionnement")
    public Long getIdDetailAppro() {
        return idDetailAppro;
    }

    public void setIdDetailAppro(Long idDetailAppro) {
        this.idDetailAppro = idDetailAppro;
    }

    @ManyToOne
    @JoinColumn(name = "id_approvisionnement")
    public EuApprovisionnement getApprovisionnement() {
        return approvisionnement;
    }

    public void setApprovisionnement(EuApprovisionnement approvisionnement) {
        this.approvisionnement = approvisionnement;
    }

    @Column(name = "code_compte")
    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    @Column(name = "code_capa")
    public String getCodeCapa() {
        return codeCapa;
    }

    public void setCodeCapa(String codeCapa) {
        this.codeCapa = codeCapa;
    }

    @Column(name = "montant_detail_approvisionnement")
    public Double getMontantDetailAppro() {
        return montantDetailAppro;
    }

    public void setMontantDetailAppro(Double montantDetailAppro) {
        this.montantDetailAppro = montantDetailAppro;
    }

    @Column(name = "id_credit")
    public Long getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Long idCredit) {
        this.idCredit = idCredit;
    }

    @Column(name = "id_traite")
    public Long getIdTraite() {
        return idTraite;
    }

    public void setIdTraite(Long idTraite) {
        this.idTraite = idTraite;
    }
}
