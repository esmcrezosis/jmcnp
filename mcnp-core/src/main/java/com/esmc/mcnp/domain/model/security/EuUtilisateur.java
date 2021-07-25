package com.esmc.mcnp.model.security;

import com.esmc.mcnp.commons.model.PageBean;
import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.model.odd.EuCentres;
import com.esmc.mcnp.model.org.EuAgence;
import com.esmc.mcnp.model.org.EuSecteur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_utilisateur database table.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "eu_utilisateur")
@NamedQuery(name = "EuUtilisateur.findAll", query = "SELECT e FROM EuUtilisateur e")
public class EuUtilisateur extends PageBean<EuUtilisateur> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur", unique = true, nullable = false)
    private Long idUtilisateur;
    @Column(name = "nom_utilisateur", length = 100)
    private String nomUtilisateur;
    @Column(name = "prenom_utilisateur", length = 100)
    private String prenomUtilisateur;
    @Column(name = "code_membre", length = 50)
    private String codeMembre;
    @Column(nullable = false, unique = true, length = 60)
    private String login;
    @Column(nullable = false, length = 100)
    private String pwd;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "ch_pwd_flog")
    private Integer chPwdFlog;
    private int ulock;
    private Integer connecte;
    @Column(name = "code_passe", length = 100)
    private String codePasse;
	@Column(name = "question_secrete", length = 255)
    private String questionSecrete;
	@Column(length = 255)
    private String reponse;
    @Column(name = "id_utilisateur_parent")
    private Long idUtilisateurParent;
    @Column(name = "code_acteur", length = 25)
    private String codeActeur;
    @Column(name = "code_gac_filiere", length = 50)
    private String codeGacFiliere;
    @Column(name = "code_groupe_create", length = 100)
    private String codeGroupeCreate;
    @Column(name = "code_zone", length = 50)
    private String codeZone;
    @Column(length = 255)
    private String description;
    @Column(name = "id_filiere")
    private Integer idFiliere;
    @Column(name = "id_pays")
    private Integer idPays;
    @Column(name = "code_tegc")
    private String codeTegc;
    private String role;
    private String section;
    private String odd;
    private String cible;
    private String indicateur;
    @Column(name = "date_creation")
    private Date dateCreation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centres")
    private EuCentres centre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agences_odd")
    private EuAgencesOdd agencesOdd;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_groupe")
    private EuUserGroup euUserGroup;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_agence")
    private EuAgence euAgence;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_secteur")
    private EuSecteur euSecteur;
    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private List<EuUserRolesPermission> permissions;
    /**
     * nom de l'institution
     */
    @Transient
    private String orgName;
    /**
     * ID de rôle
     */
    @Transient
    private List<Object> roleIdList;
    /**
     *Nom de rôle
     */
    @Transient
    private List<Object> roleNameList;

    public EuUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

}
