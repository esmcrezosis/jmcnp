package com.esmc.mcnp.domain.entity.franchise;

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

import com.esmc.mcnp.domain.entity.acteur.EuAssociation;
import com.esmc.mcnp.domain.entity.odd.EuOdd;
import com.esmc.mcnp.domain.entity.odd.EuTypeCentres;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.domain.entity.org.EuPays;
import com.esmc.mcnp.domain.entity.org.EuPrefecture;
import com.esmc.mcnp.domain.entity.org.EuRegion;
import com.esmc.mcnp.domain.entity.org.EuZone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "eu_achat_franchises")
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
	@JoinColumn(name = "id_type_franchise")
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
	@JoinColumn(name = "id_prefectures")
	private EuPrefecture prefecture;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_regions")
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
