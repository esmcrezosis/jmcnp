package com.esmc.mcnp.domain.entity.obps;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.enums.ChaineDistributionEnum;
import com.esmc.mcnp.domain.entity.odd.EuAgencesOdd;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "eu_chaine_distribution")
public class EuChaineDistribution implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "code_membre_morale")
    private EuMembreMorale proprio;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_create")
    private LocalDate dateCreate;
    private String nom;
    private String adresse;
    @Column(name = "code_postal")
    private String codePostal;
    private String ville;
    private String telephone;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_chaine")
    private ChaineDistributionEnum typeChaine;
    private Boolean autonome;
    private Boolean valider;
    @ManyToOne
    @JoinColumn(name = "id_chaine_parent")
    private EuChaineDistribution parent;
    @ManyToOne
    @JoinColumn(name = "id_canton")
    private EuCanton canton;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agences_odd")
    private EuAgencesOdd agencesOdd;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private EuUtilisateur userCreate;
}
