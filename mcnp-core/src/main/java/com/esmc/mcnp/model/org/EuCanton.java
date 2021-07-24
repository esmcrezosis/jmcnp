package com.esmc.mcnp.model.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the eu_canton database table.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "eu_canton")
@NamedQuery(name = "EuCanton.findAll", query = "SELECT e FROM EuCanton e")
public class EuCanton implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canton")
    private Integer idCanton;
    @Column(name = "nom_canton")
    private String nomCanton;
    @Column(name = "id_ville")
    private Integer idVille;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ville", insertable = false, updatable = false)
    private EuVille euVille;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prefecture")
    private EuPrefecture euPrefecture;

    public EuCanton(Integer idCanton) {
        this.idCanton = idCanton;
    }
}