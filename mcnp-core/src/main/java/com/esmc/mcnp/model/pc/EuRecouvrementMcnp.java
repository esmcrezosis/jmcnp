package com.esmc.mcnp.model.pc;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 23/05/2017.
 */
@Entity
@Table(name = "eu_recouvrement_mcnp")
public class EuRecouvrementMcnp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idRecouvrementMcnp;
	private Date dateRecouvrement;
	private Double montRecouvrement;
	private Integer idReleve;
	private String oldCodeMembre;
	private String newCodeMembre;
	private String typeRessource;
	private Long idUtilisateur;
	private Long idEchange;
	private String codeSmcipn;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recouvrement_mcnp")
	public Integer getIdRecouvrementMcnp() {
		return idRecouvrementMcnp;
	}

	public void setIdRecouvrementMcnp(Integer idRecouvrementMcnp) {
		this.idRecouvrementMcnp = idRecouvrementMcnp;
	}

	@Column(name = "date_recouvrement_mcnp")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateRecouvrement() {
		return dateRecouvrement;
	}

	public void setDateRecouvrement(Date dateRecouvrement) {
		this.dateRecouvrement = dateRecouvrement;
	}

	@Column(name = "mont_recouvrement_mcnp")
	public Double getMontRecouvrement() {
		return montRecouvrement;
	}

	public void setMontRecouvrement(Double montRecouvrement) {
		this.montRecouvrement = montRecouvrement;
	}

	@Column(name = "id_releve")
	public Integer getIdReleve() {
		return idReleve;
	}

	public void setIdReleve(Integer idReleve) {
		this.idReleve = idReleve;
	}

	@Column(name = "old_code_membre")
	public String getOldCodeMembre() {
		return oldCodeMembre;
	}

	public void setOldCodeMembre(String oldCodeMembre) {
		this.oldCodeMembre = oldCodeMembre;
	}

	@Column(name = "new_code_membre")
	public String getNewCodeMembre() {
		return newCodeMembre;
	}

	public void setNewCodeMembre(String newCodeMembre) {
		this.newCodeMembre = newCodeMembre;
	}

	@Column(name = "type_ressource")
	public String getTypeRessource() {
		return typeRessource;
	}

	public void setTypeRessource(String typeRessource) {
		this.typeRessource = typeRessource;
	}

	@Column(name = "id_utilisateur")
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	@Column(name = "id_echange")
	public Long getIdEchange() {
		return idEchange;
	}

	public void setIdEchange(Long idEchange) {
		this.idEchange = idEchange;
	}

	@Column(name = "code_smcipn")
	public String getCodeSmcipn() {
		return codeSmcipn;
	}

	public void setCodeSmcipn(String codeSmcipn) {
		this.codeSmcipn = codeSmcipn;
	}
}
