package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_faq database table.
 * 
 */
@Entity
@Table(name="eu_faq")
@NamedQuery(name="EuFaq.findAll", query="SELECT e FROM EuFaq e")
public class EuFaq implements Serializable {
	private static final long serialVersionUID = 1L;
	private double faqId;
	private double faqCategorie;
	private String faqQuestion;
	private String faqReponse;
	private String faqType;
	private double publier;

	public EuFaq() {
	}


	@Id
	@Column(name="faq_id", unique=true, nullable=false)
	public double getFaqId() {
		return this.faqId;
	}

	public void setFaqId(double faqId) {
		this.faqId = faqId;
	}


	@Column(name="faq_categorie")
	public double getFaqCategorie() {
		return this.faqCategorie;
	}

	public void setFaqCategorie(double faqCategorie) {
		this.faqCategorie = faqCategorie;
	}


	@Column(name="faq_question", length=250)
	public String getFaqQuestion() {
		return this.faqQuestion;
	}

	public void setFaqQuestion(String faqQuestion) {
		this.faqQuestion = faqQuestion;
	}


	@Lob
	@Column(name="faq_reponse")
	public String getFaqReponse() {
		return this.faqReponse;
	}

	public void setFaqReponse(String faqReponse) {
		this.faqReponse = faqReponse;
	}


	@Column(name="faq_type", length=10)
	public String getFaqType() {
		return this.faqType;
	}

	public void setFaqType(String faqType) {
		this.faqType = faqType;
	}


	public double getPublier() {
		return this.publier;
	}

	public void setPublier(double publier) {
		this.publier = publier;
	}

}