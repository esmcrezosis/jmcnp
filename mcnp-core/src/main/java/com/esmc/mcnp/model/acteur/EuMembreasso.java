package com.esmc.mcnp.model.acteur;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "eu_membreasso")
public class EuMembreasso implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long membreassoId;
    private String membreassoNom;
    private String membreassoPrenom;
    private String membreassoMobile;
    private Integer publier;
    private Long membreassoAssociation;
    private String membreassoEmail;
    private String membreassoLogin;
    private String membreassoPasse;
    private Integer membreassoType;
    private Timestamp membreassoDate;
    private Integer local;
    private Integer souscriptionId;
    private Integer integrateurId;
    private String codeMembre;
    private Integer desactiver;

    @Id
    @Column(name = "membreasso_id")
    public Long getMembreassoId() {
        return membreassoId;
    }

    public void setMembreassoId(Long membreassoId) {
        this.membreassoId = membreassoId;
    }

    @Basic
    @Column(name = "membreasso_nom")
    public String getMembreassoNom() {
        return membreassoNom;
    }

    public void setMembreassoNom(String membreassoNom) {
        this.membreassoNom = membreassoNom;
    }

    @Basic
    @Column(name = "membreasso_prenom")
    public String getMembreassoPrenom() {
        return membreassoPrenom;
    }

    public void setMembreassoPrenom(String membreassoPrenom) {
        this.membreassoPrenom = membreassoPrenom;
    }

    @Basic
    @Column(name = "membreasso_mobile")
    public String getMembreassoMobile() {
        return membreassoMobile;
    }

    public void setMembreassoMobile(String membreassoMobile) {
        this.membreassoMobile = membreassoMobile;
    }

    @Basic
    @Column(name = "publier")
    public Integer getPublier() {
        return publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Basic
    @Column(name = "membreasso_association")
    public Long getMembreassoAssociation() {
        return membreassoAssociation;
    }

    public void setMembreassoAssociation(Long membreassoAssociation) {
        this.membreassoAssociation = membreassoAssociation;
    }

    @Basic
    @Column(name = "membreasso_email")
    public String getMembreassoEmail() {
        return membreassoEmail;
    }

    public void setMembreassoEmail(String membreassoEmail) {
        this.membreassoEmail = membreassoEmail;
    }

    @Basic
    @Column(name = "membreasso_login")
    public String getMembreassoLogin() {
        return membreassoLogin;
    }

    public void setMembreassoLogin(String membreassoLogin) {
        this.membreassoLogin = membreassoLogin;
    }

    @Basic
    @Column(name = "membreasso_passe")
    public String getMembreassoPasse() {
        return membreassoPasse;
    }

    public void setMembreassoPasse(String membreassoPasse) {
        this.membreassoPasse = membreassoPasse;
    }

    @Basic
    @Column(name = "membreasso_type")
    public Integer getMembreassoType() {
        return membreassoType;
    }

    public void setMembreassoType(Integer membreassoType) {
        this.membreassoType = membreassoType;
    }

    @Basic
    @Column(name = "membreasso_date")
    public Timestamp getMembreassoDate() {
        return membreassoDate;
    }

    public void setMembreassoDate(Timestamp membreassoDate) {
        this.membreassoDate = membreassoDate;
    }

    @Basic
    @Column(name = "local")
    public Integer getLocal() {
        return local;
    }

    public void setLocal(Integer local) {
        this.local = local;
    }

    @Basic
    @Column(name = "souscription_id")
    public Integer getSouscriptionId() {
        return souscriptionId;
    }

    public void setSouscriptionId(Integer souscriptionId) {
        this.souscriptionId = souscriptionId;
    }

    @Basic
    @Column(name = "integrateur_id")
    public Integer getIntegrateurId() {
        return integrateurId;
    }

    public void setIntegrateurId(Integer integrateurId) {
        this.integrateurId = integrateurId;
    }

    @Basic
    @Column(name = "code_membre")
    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Basic
    @Column(name = "desactiver")
    public Integer getDesactiver() {
        return desactiver;
    }

    public void setDesactiver(Integer desactiver) {
        this.desactiver = desactiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuMembreasso that = (EuMembreasso) o;
        return Double.compare(that.membreassoId, membreassoId) == 0 &&
                desactiver == that.desactiver &&
                Objects.equals(membreassoNom, that.membreassoNom) &&
                Objects.equals(membreassoPrenom, that.membreassoPrenom) &&
                Objects.equals(membreassoMobile, that.membreassoMobile) &&
                Objects.equals(publier, that.publier) &&
                Objects.equals(membreassoAssociation, that.membreassoAssociation) &&
                Objects.equals(membreassoEmail, that.membreassoEmail) &&
                Objects.equals(membreassoLogin, that.membreassoLogin) &&
                Objects.equals(membreassoPasse, that.membreassoPasse) &&
                Objects.equals(membreassoType, that.membreassoType) &&
                Objects.equals(membreassoDate, that.membreassoDate) &&
                Objects.equals(local, that.local) &&
                Objects.equals(souscriptionId, that.souscriptionId) &&
                Objects.equals(integrateurId, that.integrateurId) &&
                Objects.equals(codeMembre, that.codeMembre);
    }

    @Override
    public int hashCode() {

        return Objects.hash(membreassoId, membreassoNom, membreassoPrenom, membreassoMobile, publier, membreassoAssociation, membreassoEmail, membreassoLogin, membreassoPasse, membreassoType, membreassoDate, local, souscriptionId, integrateurId, codeMembre, desactiver);
    }
}
