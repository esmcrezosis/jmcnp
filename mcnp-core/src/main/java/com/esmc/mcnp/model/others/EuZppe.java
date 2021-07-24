package com.esmc.mcnp.model.others;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_zppe database table.
 *
 */
@Entity
@Table(name = "eu_zppe")
@NamedQuery(name = "EuZppe.findAll", query = "SELECT e FROM EuZppe e")
public class EuZppe implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer zppeId;
    private Integer publier;
    private String zppeCodeMembre;
    private Date zppeDateGenere;
    private String zppeDescription;
    private String zppeEmail;
    private String zppeLibelle;
    private String zppeLogin;
    private String zppePassword;
    private String zppePortable;
    private String zppeResume;
    private String zppeVignette;

    public EuZppe() {
    }

    @Id
    @Column(name = "zppe_id", unique = true, nullable = false)
    public Integer getZppeId() {
        return this.zppeId;
    }

    public void setZppeId(Integer zppeId) {
        this.zppeId = zppeId;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Column(name = "zppe_code_membre", length = 25)
    public String getZppeCodeMembre() {
        return this.zppeCodeMembre;
    }

    public void setZppeCodeMembre(String zppeCodeMembre) {
        this.zppeCodeMembre = zppeCodeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "zppe_date_genere")
    public Date getZppeDateGenere() {
        return this.zppeDateGenere;
    }

    public void setZppeDateGenere(Date zppeDateGenere) {
        this.zppeDateGenere = zppeDateGenere;
    }

    @Lob
    @Column(name = "zppe_description")
    public String getZppeDescription() {
        return this.zppeDescription;
    }

    public void setZppeDescription(String zppeDescription) {
        this.zppeDescription = zppeDescription;
    }

    @Column(name = "zppe_email", length = 100)
    public String getZppeEmail() {
        return this.zppeEmail;
    }

    public void setZppeEmail(String zppeEmail) {
        this.zppeEmail = zppeEmail;
    }

    @Column(name = "zppe_libelle", length = 100)
    public String getZppeLibelle() {
        return this.zppeLibelle;
    }

    public void setZppeLibelle(String zppeLibelle) {
        this.zppeLibelle = zppeLibelle;
    }

    @Column(name = "zppe_login", length = 25)
    public String getZppeLogin() {
        return this.zppeLogin;
    }

    public void setZppeLogin(String zppeLogin) {
        this.zppeLogin = zppeLogin;
    }

    @Column(name = "zppe_password", length = 25)
    public String getZppePassword() {
        return this.zppePassword;
    }

    public void setZppePassword(String zppePassword) {
        this.zppePassword = zppePassword;
    }

    @Column(name = "zppe_portable", length = 25)
    public String getZppePortable() {
        return this.zppePortable;
    }

    public void setZppePortable(String zppePortable) {
        this.zppePortable = zppePortable;
    }

    @Column(name = "zppe_resume", length = 255)
    public String getZppeResume() {
        return this.zppeResume;
    }

    public void setZppeResume(String zppeResume) {
        this.zppeResume = zppeResume;
    }

    @Column(name = "zppe_vignette", length = 255)
    public String getZppeVignette() {
        return this.zppeVignette;
    }

    public void setZppeVignette(String zppeVignette) {
        this.zppeVignette = zppeVignette;
    }

}
