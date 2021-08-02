package com.esmc.mcnp.domain.entity.cm;

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

import com.esmc.mcnp.domain.entity.acteur.*;
import com.esmc.mcnp.domain.entity.ba.EuDetailSmsmoney;
import com.esmc.mcnp.domain.entity.ba.EuDetailVentesms;
import com.esmc.mcnp.domain.entity.ba.EuNn;
import com.esmc.mcnp.domain.entity.bc.EuCreditAffecter;
import com.esmc.mcnp.domain.entity.bc.EuDetailDomicilie;
import com.esmc.mcnp.domain.entity.bc.EuDomiciliation;
import com.esmc.mcnp.domain.entity.cmfh.EuDomicilieMf107;
import com.esmc.mcnp.domain.entity.cmfh.EuDomicilieMf11000;
import com.esmc.mcnp.domain.entity.mprg.EuKrr;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockes;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.domain.entity.obpsd.EuFgfn;
import com.esmc.mcnp.domain.entity.odd.EuAgencesOdd;
import com.esmc.mcnp.domain.entity.oi.EuCaps;
import com.esmc.mcnp.domain.entity.org.EuAgence;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.domain.entity.org.EuPays;
import com.esmc.mcnp.domain.entity.others.*;
import com.esmc.mcnp.domain.entity.pc.EuRapprochementcapafgfn;
import com.esmc.mcnp.domain.entity.pc.EuRapprochementsms;
import com.esmc.mcnp.domain.entity.pc.EuSalaireAffecter;
import com.esmc.mcnp.domain.entity.smcipn.EuAlerte;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipn;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * The persistent class for the eu_membre_morale database table.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "eu_membre_morale")
@DynamicUpdate
@DynamicInsert
@NamedQuery(name = "EuMembreMorale.findAll", query = "SELECT e FROM EuMembreMorale e")
public class EuMembreMorale implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "code_membre_morale", unique = true, nullable = false, length = 100)
    private String codeMembreMorale;
    @Column(name = "raison_sociale", length = 255)
    private String raisonSociale;
    @Column(name = "num_registre_membre", length = 200)
    private String numRegistreMembre;
    @Column(name = "bp_membre", length = 200)
    private String bpMembre;
    @Column(name = "tel_membre", length = 100)
    private String telMembre;
    @Column(name = "portable_membre", length = 100)
    private String portableMembre;
    @Column(name = "email_membre", length = 255)
    private String emailMembre;
    @Column(name = "site_web", length = 200)
    private String siteWeb;
    @Column(name = "quartier_membre", length = 255)
    private String quartierMembre;
    @Column(name = "ville_membre", length = 100)
    private String villeMembre;
    @Column(name = "domaine_activite", length = 255)
    private String domaineActivite;
    @Column(name = "type_fournisseur")
    private String typeFournisseur;
    @Column(name = "code_gac_filiere", length = 100)
    private String codeGacFiliere;
    @Column(name = "code_type_acteur", length = 100)
    private String codeTypeActeur;
    @Column(length = 75)
    private String codesecret;
    @Column(name = "etat_membre", length = 4)
    private String etatMembre;
    @Column(name = "auto_enroler", length = 4)
    private String autoEnroler;
    private Integer desactiver;
    @Column(name = "id_filiere")
    private Long idFiliere;
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;
    @Column(name = "heure_identification")
    private LocalTime heureIdentification;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_identification")
    private Date dateIdentification;
    @JsonIgnore
    @Lob
    @Column(name = "photo_mpp")
    private byte[] photoMpp;
    @JsonIgnore
    @Lob
    private byte[] photompp;
    @JsonIgnore
    @Lob
    private byte[] empreinte1;
    @JsonIgnore
    @Lob
    private byte[] empreinte2;
    @JsonIgnore
    @Lob
    private byte[] empreinte3;
    @JsonIgnore
    @Lob
    private byte[] empreinte4;
    @JsonIgnore
    @Lob
    private byte[] empreinte5;
    @ManyToOne
    @JoinColumn(name = "code_statut")
    private EuStatutJuridique euStatutJuridique;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_canton")
    private EuCanton euCanton;
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
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuAchatCredit> euAchatCredits;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuActeursCreneaux> euActeursCreneauxs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale1", fetch = FetchType.LAZY)
    private List<EuAlerte> euAlertes1;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale2", fetch = FetchType.LAZY)
    private List<EuAlerte> euAlertes2;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale3", fetch = FetchType.LAZY)
    private List<EuAlerte> euAlertes3;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuArticleStockes> euArticleStockes;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuBesoin> euBesoins;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuCaps> euCaps;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuCompte> euComptes;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuCreditAffecter> euCreditAffecters;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuCreneaux> euCreneauxs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuDemandeTransfert> euDemandeTransferts;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuDetailDomicilie> euDetailDomicilies;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuDetailSmsmoney> euDetailSmsmoneys;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuDetailVentesms> euDetailVentesms;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuDomiciliation> euDomiciliations;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuDomicilieMf107> euDomicilieMf107s;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuDomicilieMf11000> euDomicilieMf11000s;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuEchange> euEchanges;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale1", fetch = FetchType.LAZY)
    private List<EuFacture> euFactures1;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale2", fetch = FetchType.LAZY)
    private List<EuFacture> euFactures2;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuFgfn> euFgfns;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuFl> euFls;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuFs> euFs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuGac> euGacs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuGacFiliere> euGacFilieres;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuKrr> euKrrs;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuLouer> euLouers;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuNn> euNns;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuOperation> euOperations;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuProforma> euProformas;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuProprietaire> euProprietaires;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale1", fetch = FetchType.LAZY)
    private List<EuRapprochementcapafgfn> euRapprochementcapafgfns1;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale2", fetch = FetchType.LAZY)
    private List<EuRapprochementcapafgfn> euRapprochementcapafgfns2;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale1", fetch = FetchType.LAZY)
    private List<EuRapprochementsms> euRapprochementsms1;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale2", fetch = FetchType.LAZY)
    private List<EuRapprochementsms> euRapprochementsms2;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuRepresentation> euRepresentations;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuSalaireAffecter> euSalaireAffecters;
    @JsonIgnore
    @OneToMany(mappedBy = "membreMorale", fetch = FetchType.LAZY)
    private List<EuSmcipn> euSmcipns;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuSmcipnp> euSmcipnps;
    @JsonIgnore
    @OneToMany(mappedBy = "euMembreMorale", fetch = FetchType.LAZY)
    private List<EuTegc> euTegcs;

    public EuMembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }
}