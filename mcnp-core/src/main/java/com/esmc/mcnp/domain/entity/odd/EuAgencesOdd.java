package com.esmc.mcnp.domain.entity.odd;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * The persistent class for the eu_agences_odd database table.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "eu_agences_odd")
@NamedQuery(name = "EuAgencesOdd.findAll", query = "SELECT e FROM EuAgencesOdd e")
public class EuAgencesOdd implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agences_odd")
    private Integer idAgencesOdd;
    @Column(name = "code_agences_odd")
    private String codeAgencesOdd;
    @Column(name = "date_agences_odd")
    private LocalDate dateAgencesOdd;
    @Column(name = "libelle_agences_odd")
    private String libelleAgencesOdd;
    @Column(name = "reference_agences_odd")
    private String referenceAgencesOdd;
    @Column(name = "addresse_agences_odd")
    private String adresseAgencesOdd;
    @Column(name = "bp_agences_odd")
    private String bpAgencesOdd;
    @Column(name = "telephone_agences_odd")
    private String telephoneAgencesOdd;
    @Column(name = "agences_odd_source")
    private Boolean agencesOddSource;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_morale")
    private EuMembreMorale membre;

    //bi-directional many-to-one association to EuOdd
    @ManyToOne
    @JoinColumn(name = "id_odd")
    private EuOdd euOdd;

    //bi-directional many-to-one association to EuCentre
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_centres")
    private EuCentres euCentre;

    @ManyToOne
    @JoinColumn(name = "id_canton")
    private EuCanton euCanton;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private EuUtilisateur user;

    //bi-directional many-to-one association to EuCentrale
    @JsonIgnore
    @OneToMany(mappedBy = "euAgencesOdd")
    private List<EuCentrales> euCentrales;

    public EuCentrales addEuCentrale(EuCentrales euCentrale) {
        getEuCentrales().add(euCentrale);
        euCentrale.setEuAgencesOdd(this);

        return euCentrale;
    }

    public EuCentrales removeEuCentrale(EuCentrales euCentrale) {
        getEuCentrales().remove(euCentrale);
        euCentrale.setEuAgencesOdd(null);

        return euCentrale;
    }

}