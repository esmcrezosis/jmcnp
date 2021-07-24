package com.esmc.mcnp.model.sms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="eu_sms_sent")
@NamedQuery(name="EuSmsSent.findAll", query="SELECT e FROM EuSmsSent e")
public class EuSmsSent implements Serializable{
	private static final long serialVersionUID = 1L;
	Long neng;
	String datetime;
	Integer etat;
	String recipient;
	String smsbody;
	String typeexpediteur;
	Integer msgid;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "neng", unique=true, nullable=false)
	public Long getNeng() {
		return neng;
	}
	public void setNeng(Long neng) {
		this.neng = neng;
	}
	
	 @Column(name = "datetime", length = 80)
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	 @Column(name = "etat")
	public Integer getEtat() {
		return etat;
	}
	public void setEtat(Integer etat) {
		this.etat = etat;
	}
	
	 @Column(name = "recipient", length = 60)
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	 @Column(name = "smsbody")
	public String getSmsbody() {
		return smsbody;
	}
	public void setSmsbody(String smsbody) {
		this.smsbody = smsbody;
	}
	
	 @Column(name = "typeexpediteur", length = 80)
	public String getTypeexpediteur() {
		return typeexpediteur;
	}
	public void setTypeexpediteur(String typeexpediteur) {
		this.typeexpediteur = typeexpediteur;
	}
	
	@Column(name = "msgid")
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
	
	
	
}
