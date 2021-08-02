package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.org.EuDivisionGac;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

/**
 * Entity implementation class for Entity: EuLiaisonUser
 *
 */
@Entity
@Table(name = "eu_liaison_user")
public class EuLiaisonUser implements Serializable {

	private Integer id;
	private EuUtilisateur utilisateur;
	private EuDivisionGac euDivisionGac;
	private Date dateLiaison;
	private static final long serialVersionUID = 1L;

	public EuLiaisonUser() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_liaison_user")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "id_utilisateur")
	public EuUtilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(EuUtilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@ManyToOne
	@JoinColumn(name = "id_division_gac")
	public EuDivisionGac getEuDivisionGac() {
		return euDivisionGac;
	}

	public void setEuDivisionGac(EuDivisionGac euDivisionGac) {
		this.euDivisionGac = euDivisionGac;
	}

	@Column(name = "date_liaison")
	public Date getDateLiaison() {
		return this.dateLiaison;
	}

	public void setDateLiaison(Date dateLiaison) {
		this.dateLiaison = dateLiaison;
	}

}
