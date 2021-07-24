package com.esmc.mcnp.model.cm;

import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eu_statut_juridique database table.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "eu_statut_juridique")
@NamedQuery(name = "EuStatutJuridique.findAll", query = "SELECT e FROM EuStatutJuridique e")
public class EuStatutJuridique implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "code_statut", unique = true, nullable = false, length = 100)
    private String codeStatut;
    @Column(name = "libelle_statut", nullable = false, length = 100)
    private String libelleStatut;
    @Column(name = "type_statut", length = 100)
    private String typeStatut;
    @JsonIgnore
    @OneToMany(mappedBy = "euStatutJuridique")
    private List<EuMembreMorale> euMembreMorales;

    public EuStatutJuridique(String codeStatut) {
        this.codeStatut = codeStatut;
    }
}