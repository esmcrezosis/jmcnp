package com.esmc.mcnp.domain.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "eu_groupe_roles")
@Accessors(chain = true)
public class EuGroupeRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_groupe_roles")
    private Integer idGroupeRoles;
    @Column(name = "libelle_groupe_roles")
    private String libelleGroupeRoles;
    @ManyToOne
    @JoinColumn(name = "parent_groupe_roles")
    private EuGroupeRoles parentGroupeRoles;
    @JsonIgnore
    @OneToMany(mappedBy="groupeRoles")
    private List<EuRoles> euRoles;
}
