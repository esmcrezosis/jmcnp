package com.esmc.mcnp.domain.entity.org;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.others.EuSubSecteur;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_secteur database table.
 * 
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name="eu_secteur")
@NamedQuery(name="EuSecteur.findAll", query="SELECT e FROM EuSecteur e")
public class EuSecteur implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="code_secteur", unique=true, nullable=false, length=100)
	private String codeSecteur;
	@Column(name="code_membre", length=25)
	private String codeMembre;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	private Date dateCreation;
	@Column(name="nom_secteur", nullable=false, length=150)
	private String nomSecteur;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_region")
	private EuRegion euRegion;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_pays")
	private EuPays euPay;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_prefecture")
	private EuPrefecture euPrefecture;
	@OneToMany(mappedBy="euSecteur")
	private List<EuSubSecteur> euSubSecteurs;
	@OneToMany(mappedBy="euSecteur")
	private List<EuUtilisateur> euUtilisateurs;

	public EuSubSecteur addEuSubSecteur(EuSubSecteur euSubSecteur) {
		getEuSubSecteurs().add(euSubSecteur);
		euSubSecteur.setEuSecteur(this);

		return euSubSecteur;
	}

	public EuSubSecteur removeEuSubSecteur(EuSubSecteur euSubSecteur) {
		getEuSubSecteurs().remove(euSubSecteur);
		euSubSecteur.setEuSecteur(null);

		return euSubSecteur;
	}

	public EuUtilisateur addEuUtilisateur(EuUtilisateur euUtilisateur) {
		getEuUtilisateurs().add(euUtilisateur);
		euUtilisateur.setEuSecteur(this);

		return euUtilisateur;
	}

	public EuUtilisateur removeEuUtilisateur(EuUtilisateur euUtilisateur) {
		getEuUtilisateurs().remove(euUtilisateur);
		euUtilisateur.setEuSecteur(null);

		return euUtilisateur;
	}

}