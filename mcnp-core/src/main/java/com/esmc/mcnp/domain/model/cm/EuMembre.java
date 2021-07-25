package com.esmc.mcnp.model.cm;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.acteur.*;
import com.esmc.mcnp.model.bc.EuDetailDomicilie;
import com.esmc.mcnp.model.cmfh.EuDomicilieMf107;
import com.esmc.mcnp.model.cmfh.EuDomicilieMf11000;
import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.oi.EuBnpSqmax;
import com.esmc.mcnp.model.oi.EuCaps;
import com.esmc.mcnp.model.org.EuAgence;
import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.model.org.EuPays;
import com.esmc.mcnp.model.others.*;
import com.esmc.mcnp.model.pc.EuSalaireAffecter;
import com.esmc.mcnp.model.smcipn.EuAlerte;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * The persistent class for the eu_membre database table.
 *
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "eu_membre")
@DynamicUpdate
@DynamicInsert
@NamedQuery(name = "EuMembre.findAll", query = "SELECT e FROM EuMembre e")
public class EuMembre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "code_membre", unique = true, nullable = false, length = 100)
    private String codeMembre;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_nais_membre")
    private Date dateNaisMembre;
    @Column(name = "lieu_nais_membre", length = 200)
    private String lieuNaisMembre;
    @Column(name = "mere_membre", length = 200)
    private String mereMembre;
    @Column(length = 255)
    private String mifarecard;
    @Column(name = "nbr_enf_membre")
    private Integer nbrEnfMembre;
    @Column(name = "nom_membre", length = 200)
    private String nomMembre;
    @Column(name = "pere_membre", length = 200)
    private String pereMembre;
    @Column(name = "portable_membre", length = 100)
    private String portableMembre;
    @Column(name = "prenom_membre", length = 200)
    private String prenomMembre;
    @Column(name = "profession_membre", length = 200)
    private String professionMembre;
    @Column(name = "quartier_membre", length = 200)
    private String quartierMembre;
    @Column(name = "sexe_membre", length = 36)
    private String sexeMembre;
    @Column(name = "sitfam_membre", length = 200)
    private String sitfamMembre;
    @Column(name = "tel_membre", length = 100)
    private String telMembre;
    @Column(name = "bp_membre", length = 100)
    private String bpMembre;
    @Column(name = "email_membre", length = 150)
    private String emailMembre;
    @Column(name = "ville_membre", length = 200)
    private String villeMembre;
    @Column(name = "auto_enroler", length = 4)
    private String autoEnroler;
    @Column(name = "code_gac", length = 15)
    private String codeGac;
    @Column(name = "etat_membre", length = 1)
    private String etatMembre;
    @Column(length = 200)
    private String formation;
    private Integer desactiver;
    @Column(length = 75)
    private String codesecret;
    @Lob
    @Column(name = "photo_mpp")
    private byte[] photoMpp;
    @Lob
    private byte[] photompp;
    @Column(name = "heure_identification")
    private LocalTime heureIdentification;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_identification")
    private Date dateIdentification;
    @Column(name = "id_maison")
    private Long idMaison;
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;
    @Lob
    private byte[] empreinte1;
    @Lob
    @JsonIgnore
    private byte[] empreinte2;
    @Lob
    @JsonIgnore
    private byte[] empreinte3;
    @Lob
    @JsonIgnore
    private byte[] empreinte4;
    @Lob
    @JsonIgnore
    private byte[] empreinte5;
    @Lob
    @JsonIgnore
    private byte[] empreinte6;
    @Lob
    @JsonIgnore
    private byte[] empreinte7;
    @Lob
    @JsonIgnore
    private byte[] empreinte8;
    @Lob
    @JsonIgnore
    private byte[] empreinte9;
    @Lob
    @JsonIgnore
    private byte[] empreinte10;
    @Lob
    @JsonIgnore
    private byte[] empreinte11;
    @Lob
    @JsonIgnore
    private byte[] empreinte12;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_religion_membre")
    private EuReligion euReligion;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_pays")
    private EuPays euPay;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_agence")
    private EuAgence euAgence;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agences_odd")
    private EuAgencesOdd agencesOdd;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_canton")
    private EuCanton euCanton;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuActeursCreneaux> euActeursCreneauxs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuAlerte> euAlertes;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuBnpSqmax> euBnpSqmaxs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre1", fetch = FetchType.LAZY)
    private List<EuCaps> euCaps1;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre2", fetch = FetchType.LAZY)
    private List<EuCaps> euCaps2;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuCompte> euComptes;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuCreneaux> euCreneauxs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuDetailDomicilie> euDetailDomicilies;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuDomicilieMf107> euDomicilieMf107s;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuDomicilieMf11000> euDomicilieMf11000s;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuEchange> euEchanges;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuEmploye> euEmployes;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuFl> euFls;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuGac> euGacs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuGacFiliere> euGacFilieres;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuKrr> euKrrs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuLouer> euLouers;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuOperation> euOperations;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuProprietaire> euProprietaires;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuRepresentation> euRepresentations;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuSalaireAffecter> euSalaireAffecters;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembre", fetch = FetchType.LAZY)
    private List<EuStand> euStands;

	/**
	 * @param codeMembre
	 */
	public EuMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

}
