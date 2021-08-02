package com.esmc.mcnp.domain.entity.ot;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "eu_formation")
public class EuFormation implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_bci")
    private String codeBci;
    @Column(name = "code_membre")
    private String codeMembre;
    @Column(name = "libelle_formation")
    private String libelleFormation;
    @Column(name = "date_bci")
    private LocalDate dateBci;
    @Column(name = "id_candidature")
    private Integer idCandidature;
    @Column(name = "id_candidature_poste")
    private Integer idCandidaturePoste;
    private Boolean activer;
}
