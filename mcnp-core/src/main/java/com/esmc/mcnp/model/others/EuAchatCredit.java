package com.esmc.mcnp.model.others;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUtilisateur;

/**
 * The persistent class for the eu_achat_credit database table.
 *
 */
@Entity
@Table(name = "eu_achat_credit")
@NamedQuery(name = "EuAchatCredit.findAll", query = "SELECT e FROM EuAchatCredit e")
public class EuAchatCredit implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAchat;
    private String codeMembreAcheteur;
    private String codeSms;
    private String creditObt;
    private Date dateAchat;
    private Long idTpagcp;
    private double montAchat;
    private EuUtilisateur euUtilisateur;
    private EuMembreMorale euMembreMorale;

    public EuAchatCredit() {
    }

    @Id
    @Column(name = "id_achat", unique = true, nullable = false)
    public Long getIdAchat() {
        return this.idAchat;
    }

    public void setIdAchat(Long idAchat) {
        this.idAchat = idAchat;
    }

    @Column(name = "code_membre_acheteur", nullable = false, length = 180)
    public String getCodeMembreAcheteur() {
        return this.codeMembreAcheteur;
    }

    public void setCodeMembreAcheteur(String codeMembreAcheteur) {
        this.codeMembreAcheteur = codeMembreAcheteur;
    }

    @Column(name = "code_sms", nullable = false, length = 32)
    public String getCodeSms() {
        return this.codeSms;
    }

    public void setCodeSms(String codeSms) {
        this.codeSms = codeSms;
    }

    @Lob
    @Column(name = "credit_obt", nullable = false)
    public String getCreditObt() {
        return this.creditObt;
    }

    public void setCreditObt(String creditObt) {
        this.creditObt = creditObt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_achat", nullable = false)
    public Date getDateAchat() {
        return this.dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    @Column(name = "id_tpagcp", nullable = false)
    public Long getIdTpagcp() {
        return this.idTpagcp;
    }

    public void setIdTpagcp(Long idTpagcp) {
        this.idTpagcp = idTpagcp;
    }

    @Column(name = "mont_achat", nullable = false)
    public double getMontAchat() {
        return this.montAchat;
    }

    public void setMontAchat(double montAchat) {
        this.montAchat = montAchat;
    }

    //bi-directional many-to-one association to EuUtilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    public EuUtilisateur getEuUtilisateur() {
        return this.euUtilisateur;
    }

    public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
        this.euUtilisateur = euUtilisateur;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_vendeur", nullable = false)
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

}
