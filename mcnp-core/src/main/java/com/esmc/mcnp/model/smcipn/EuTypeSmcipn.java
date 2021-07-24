package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name = "eu_type_smcipn")
@Data
public class EuTypeSmcipn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "code_type_smcipn")
	private String codeTypeSmcipn;
	@Column(name = "nom_type_smcipn")
	private String nomTypeSmcipn;

}
