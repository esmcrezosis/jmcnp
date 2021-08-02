package com.esmc.mcnp.domain.entity.others;

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
 * The persistent class for the eu_question_reponse database table.
 *
 */
@Entity
@Table(name = "eu_question_reponse")
@NamedQuery(name = "EuQuestionReponse.findAll", query = "SELECT e FROM EuQuestionReponse e")
public class EuQuestionReponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer questionReponseId;
    private Integer publier;
    private String questionReponseCategorie;
    private Date questionReponseDate;
    private String questionReponseNom;
    private String questionReponseQuestion;
    private String questionReponseReponse;
    private Long questionReponseUtilisateur;

    public EuQuestionReponse() {
    }

    @Id
    @Column(name = "question_reponse_id", unique = true, nullable = false)
    public Integer getQuestionReponseId() {
        return this.questionReponseId;
    }

    public void setQuestionReponseId(Integer questionReponseId) {
        this.questionReponseId = questionReponseId;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Column(name = "question_reponse_categorie", length = 50)
    public String getQuestionReponseCategorie() {
        return this.questionReponseCategorie;
    }

    public void setQuestionReponseCategorie(String questionReponseCategorie) {
        this.questionReponseCategorie = questionReponseCategorie;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "question_reponse_date")
    public Date getQuestionReponseDate() {
        return this.questionReponseDate;
    }

    public void setQuestionReponseDate(Date questionReponseDate) {
        this.questionReponseDate = questionReponseDate;
    }

    @Column(name = "question_reponse_nom", length = 100)
    public String getQuestionReponseNom() {
        return this.questionReponseNom;
    }

    public void setQuestionReponseNom(String questionReponseNom) {
        this.questionReponseNom = questionReponseNom;
    }

    @Column(name = "question_reponse_question", length = 250)
    public String getQuestionReponseQuestion() {
        return this.questionReponseQuestion;
    }

    public void setQuestionReponseQuestion(String questionReponseQuestion) {
        this.questionReponseQuestion = questionReponseQuestion;
    }

    @Lob
    @Column(name = "question_reponse_reponse")
    public String getQuestionReponseReponse() {
        return this.questionReponseReponse;
    }

    public void setQuestionReponseReponse(String questionReponseReponse) {
        this.questionReponseReponse = questionReponseReponse;
    }

    @Column(name = "question_reponse_utilisateur")
    public Long getQuestionReponseUtilisateur() {
        return this.questionReponseUtilisateur;
    }

    public void setQuestionReponseUtilisateur(Long questionReponseUtilisateur) {
        this.questionReponseUtilisateur = questionReponseUtilisateur;
    }

}
