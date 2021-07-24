package com.esmc.mcnp.model.odd;

import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.org.*;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * The persistent class for the eu_centres database table.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "eu_centres")
@NamedQuery(name = "EuCentres.findAll", query = "SELECT e FROM EuCentre e")
public class EuCentres implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_centres")
    private Integer idCentres;
    @Column(name = "date_centres")
    private LocalDate dateCentres;
    @Column(name = "reference_centre")
    private String referenceCentre;
    @Column(name = "libelle_centre")
    private String libelleCentre;
    @Column(name = "surface_centre")
    private Double surfaceCentre;
    @Column(name = "telephone_centre")
    private String telephoneCentre;
    @Column(name = "bp_centre")
    private String bpCentre;
    @Column(name = "addresse_centre")
    private String adresseCentre;
    @Column(name = "type_centre")
    private String typeCentre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_morale")
    private EuMembreMorale membre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centres_parent")
    private EuCentres centreParent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_zone")
    private EuZone zone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays")
    private EuPays pays;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_region")
    private EuRegion region;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prefecture")
    private EuPrefecture prefecture;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_canton")
    private EuCanton canton;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private EuUtilisateur user;
    @JsonIgnore
    @OneToMany(mappedBy = "euCentre")
    private List<EuAgencesOdd> euAgencesOdds = Lists.newArrayList();
    @JsonIgnore
    @OneToMany(mappedBy = "centreParent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EuCentres> childCentres = Lists.newArrayList();


    public EuAgencesOdd addEuAgencesOdd(EuAgencesOdd euAgencesOdd) {
        getEuAgencesOdds().add(euAgencesOdd);
        euAgencesOdd.setEuCentre(this);

        return euAgencesOdd;
    }

    public EuAgencesOdd removeEuAgencesOdd(EuAgencesOdd euAgencesOdd) {
        getEuAgencesOdds().remove(euAgencesOdd);
        euAgencesOdd.setEuCentre(null);

        return euAgencesOdd;
    }

    public EuCentres addEuCentres(EuCentres euCentres) {
        getChildCentres().add(euCentres);
        euCentres.setCentreParent(this);

        return euCentres;
    }

    public EuCentres removeEuCentres(EuCentres euCentres) {
        getChildCentres().remove(euCentres);
        euCentres.setCentreParent(null);

        return euCentres;
    }


}