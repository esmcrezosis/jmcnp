package com.esmc.mcnp.domain.entity.op;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_appeloffres database table.
 *
 */
@Entity
@Table(name = "eu_appeloffres")
@NamedQuery(name = "EuAppeloffres.findAll", query = "SELECT e FROM EuAppeloffres e")
public class EuAppeloffres implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAppeloffres;
    private String codeMembreMorale;
    private Date dateAppeloffres;
    private String descAppeloffres;
    private Long idDocument;
    private Long idUtilisateur;
    private String libelleAppeloffres;
    private String numAppeloffres;
    private Integer okfinal;
    private Integer preselection;
    private Integer propo;
    private Integer selection;

    public EuAppeloffres() {
    }

    @Id
    @Column(name = "id_appeloffres", unique = true, nullable = false)
    public Long getIdAppeloffres() {
        return this.idAppeloffres;
    }

    public void setIdAppeloffres(Long idAppeloffres) {
        this.idAppeloffres = idAppeloffres;
    }

    @Column(name = "code_membre_morale", length = 20)
    public String getCodeMembreMorale() {
        return this.codeMembreMorale;
    }

    public void setCodeMembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_appeloffres")
    public Date getDateAppeloffres() {
        return this.dateAppeloffres;
    }

    public void setDateAppeloffres(Date dateAppeloffres) {
        this.dateAppeloffres = dateAppeloffres;
    }

    @Column(name = "desc_appeloffres", length = 255)
    public String getDescAppeloffres() {
        return this.descAppeloffres;
    }

    public void setDescAppeloffres(String descAppeloffres) {
        this.descAppeloffres = descAppeloffres;
    }

    @Column(name = "id_document")
    public Long getIdDocument() {
        return this.idDocument;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "libelle_appeloffres", length = 255)
    public String getLibelleAppeloffres() {
        return this.libelleAppeloffres;
    }

    public void setLibelleAppeloffres(String libelleAppeloffres) {
        this.libelleAppeloffres = libelleAppeloffres;
    }

    @Column(name = "num_appeloffres", length = 180)
    public String getNumAppeloffres() {
        return this.numAppeloffres;
    }

    public void setNumAppeloffres(String numAppeloffres) {
        this.numAppeloffres = numAppeloffres;
    }

    public Integer getOkfinal() {
        return this.okfinal;
    }

    public void setOkfinal(Integer okfinal) {
        this.okfinal = okfinal;
    }

    public Integer getPreselection() {
        return this.preselection;
    }

    public void setPreselection(Integer preselection) {
        this.preselection = preselection;
    }

    public Integer getPropo() {
        return this.propo;
    }

    public void setPropo(Integer propo) {
        this.propo = propo;
    }

    public Integer getSelection() {
        return this.selection;
    }

    public void setSelection(Integer selection) {
        this.selection = selection;
    }

}
