package com.esmc.mcnp.model.pc;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 23/05/2017.
 */
@Entity
@Table(name = "eu_recouvrement")
public class EuRecouvrement implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idRecouvrement;
    private String codeMembre;
    private Double montRecouvrement;
    private Double montTranche;
    private Integer periodeRenouv;
    private Date dateDebut;
    private Date dateFin;
    private Integer nbreRenouv;
    private Integer resteNbreRenouv;
    private Double montPayer;
    private Double resteAPayer;
    private String typeRessource;
    private Integer idRecouvrementMcnp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recouvrement")
    public Integer getIdRecouvrement() {
        return idRecouvrement;
    }

    public void setIdRecouvrement(Integer idRecouvrement) {
        this.idRecouvrement = idRecouvrement;
    }

    @Column(name = "code_membre")
    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "mont_recouvrement")
    public Double getMontRecouvrement() {
        return montRecouvrement;
    }

    public void setMontRecouvrement(Double montRecouvrement) {
        this.montRecouvrement = montRecouvrement;
    }

    @Column(name = "mont_tranche")
    public Double getMontTranche() {
        return montTranche;
    }

    public void setMontTranche(Double montTranche) {
        this.montTranche = montTranche;
    }

    @Column(name = "periode_renouv")
    public Integer getPeriodeRenouv() {
        return periodeRenouv;
    }

    public void setPeriodeRenouv(Integer periodeRenouv) {
        this.periodeRenouv = periodeRenouv;
    }

    @Column(name = "date_debut")
    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Column(name = "date_fin")
    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Column(name = "nbre_renouv")
    public Integer getNbreRenouv() {
        return nbreRenouv;
    }

    public void setNbreRenouv(Integer nbreRenouv) {
        this.nbreRenouv = nbreRenouv;
    }

    @Column(name = "reste_nbre_renouv")
    public Integer getResteNbreRenouv() {
        return resteNbreRenouv;
    }

    public void setResteNbreRenouv(Integer resteNbreRenouv) {
        this.resteNbreRenouv = resteNbreRenouv;
    }

    @Column(name = "mont_payer")
    public Double getMontPayer() {
        return montPayer;
    }

    public void setMontPayer(Double montPayer) {
        this.montPayer = montPayer;
    }

    @Column(name = "reste_a_payer")
    public Double getResteAPayer() {
        return resteAPayer;
    }

    public void setResteAPayer(Double resteAPayer) {
        this.resteAPayer = resteAPayer;
    }

    @Column(name = "type_ressource")
    public String getTypeRessource() {
        return typeRessource;
    }

    public void setTypeRessource(String typeRessource) {
        this.typeRessource = typeRessource;
    }

    @Column(name = "id_recouvrement_mcnp")
    public Integer getIdRecouvrementMcnp() {
        return idRecouvrementMcnp;
    }

    public void setIdRecouvrementMcnp(Integer idRecouvrementMcnp) {
        this.idRecouvrementMcnp = idRecouvrementMcnp;
    }
}
