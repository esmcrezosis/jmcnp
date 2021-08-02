package com.esmc.mcnp.domain.entity.franchise;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "eu_franchises")
public class EuActivationActeur implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_activation_acteur")
	private Integer idActivationActeur;
	@Column(name = "code_membre")
	private String codeMembre;
	@Column(name = "libelle_acteur")
	private String libelleActeur;
	private Boolean activate;
	@Column(name = "date_activation")
	private LocalDateTime dateActivation;
}
