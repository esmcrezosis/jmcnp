package com.esmc.mcnp.model.franchise;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esmc.mcnp.model.acteur.EuAssociation;
import com.esmc.mcnp.model.odd.EuOdd;
import com.esmc.mcnp.model.odd.EuTypeCentres;
import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.model.org.EuPays;
import com.esmc.mcnp.model.org.EuPrefecture;
import com.esmc.mcnp.model.org.EuRegion;
import com.esmc.mcnp.model.org.EuZone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "eu_achat_franchise")
public class EuAchatFranchises {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_achat_franchise")
	private Long idAchatFranchise;
	@Column(name = "reference_franchise")
	private String referenceFranchise;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_association")
	private EuAssociation association;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_franchises")
	private EuTypeFranchises typeFranchise;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_groupe_franchise")
	private EuGroupeFranchise groupeFranchise;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_zone")
	private EuZone zone;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_canton")
	private EuCanton canton;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prefecture")
	private EuPrefecture prefecture;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_region")
	private EuRegion region;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pays")
	private EuPays pays;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_centres")
	private EuTypeCentres typeCentre;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_odd")
	private EuOdd odd;
	@Column(name = "code_banque")
	private String codeBanque;
	@Column(name = "numero_banque")
	private String numeroBanque;
	@Column(name = "adresse_franchise")
	private String adresseFranchise;
	@Column(name = "date_achat_franchise")
	private LocalDate dateAchatFranchise;

}
