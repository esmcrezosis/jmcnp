package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "eu_reclamation")
public class EuReclamation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "date_reclamation")
	private LocalDateTime dateReclamation;
	@Enumerated(EnumType.STRING)
	@Column(name = "type_passif")
	private TypePassif typePassif;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cas_passif")
	private EuCasPassif casPassif;
	@Column(name = "code_membre")
	private String codeMembre;
	@Column(name = "ancien_code_membre")
	private String ancienCodeMembre;
	@Column(name = "numero_bon_mf")
	private String numeroBonMf;
	@Column(name = "libelle_reclamation")
	private String libelleReclamation;
	@Column(name = "description_reclamation")
	private String descriptionRec;
	@Column(name = "montant_reclamation")
	private Double montantReclamation;
	@Column(name = "montant_valide")
	private Double montantValide;
	private boolean valider;
	@Enumerated(EnumType.STRING)
	private StatutReclamation statut;
	
	public EuReclamation() {
	}

}
