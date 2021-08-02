package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_question_reponse_categorie database table.
 *
 */
@Entity
@Table(name = "eu_question_reponse_categorie")
@NamedQuery(name = "EuQuestionReponseCategorie.findAll", query = "SELECT e FROM EuQuestionReponseCategorie e")
public class EuQuestionReponseCategorie implements Serializable {

    private static final long serialVersionUID = 1L;
    private String questionReponseCategorieCod;
    private String questionReponseCategorieLib;

    public EuQuestionReponseCategorie() {
    }

    @Id
    @Column(name = "question_reponse_categorie_cod", unique = true, nullable = false, length = 70)
    public String getQuestionReponseCategorieCod() {
        return this.questionReponseCategorieCod;
    }

    public void setQuestionReponseCategorieCod(String questionReponseCategorieCod) {
        this.questionReponseCategorieCod = questionReponseCategorieCod;
    }

    @Column(name = "question_reponse_categorie_lib", length = 255)
    public String getQuestionReponseCategorieLib() {
        return this.questionReponseCategorieLib;
    }

    public void setQuestionReponseCategorieLib(String questionReponseCategorieLib) {
        this.questionReponseCategorieLib = questionReponseCategorieLib;
    }

}
