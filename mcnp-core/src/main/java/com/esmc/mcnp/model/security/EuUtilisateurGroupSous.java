package com.esmc.mcnp.model.security;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_utilisateur_group_sous database table.
 *
 */
@Entity
@Table(name = "eu_utilisateur_group_sous")
@NamedQuery(name = "EuUtilisateurGroupSous.findAll", query = "SELECT e FROM EuUtilisateurGroupSous e")
public class EuUtilisateurGroupSous implements Serializable {

    private static final long serialVersionUID = 1L;
    private EuUtilisateurGroupSousPK id;

    public EuUtilisateurGroupSous() {
    }

    @EmbeddedId
    public EuUtilisateurGroupSousPK getId() {
        return this.id;
    }

    public void setId(EuUtilisateurGroupSousPK id) {
        this.id = id;
    }

}
