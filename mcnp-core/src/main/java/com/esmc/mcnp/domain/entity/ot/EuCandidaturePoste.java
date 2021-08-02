package com.esmc.mcnp.domain.entity.ot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.esmc.mcnp.domain.entity.security.EuRoles;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "eu_candidature_postes")
@Data
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
public class EuCandidaturePoste implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_candidature_postes")
	private Integer idCandidaturePoste;
	@ManyToOne
	@JoinColumn(name = "id_candidature")
	private EuCandidature candidature;
	@ManyToOne
	@JoinColumn(name = "id_roles")
	private EuRoles poste;
	@Column(name = "nbre_personnes")
	private Integer nbrePersonne;
}
