package com.esmc.mcnp.web.mvc.dto;

import com.esmc.mcnp.model.acteur.EuMembreasso;
import com.esmc.mcnp.model.security.EuUtilisateur;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String password;
    private String passwordHash;
    private String nomUser;
    private String prenomUser;
    private boolean imprimer;
    private List<String> groupes = Lists.newArrayList();
    private String userType;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(EuUtilisateur utilisateur) {
        this.id = utilisateur.getIdUtilisateur();
        this.nomUser = utilisateur.getNomUtilisateur();
        this.prenomUser = utilisateur.getPrenomUtilisateur();
        this.userName = utilisateur.getLogin();
        this.password = utilisateur.getPwd();
        this.passwordHash = utilisateur.getPasswordHash();
        this.imprimer = false;
        if (Objects.nonNull(utilisateur.getEuUserGroup())) {
            this.groupes.add(utilisateur.getEuUserGroup().getCodeGroupe());
        }
        if (Objects.nonNull(utilisateur.getPermissions())) {
            utilisateur.getPermissions().forEach(p -> groupes.add(p.getEuRole().getCodeRoles()));
        }
    }

    public static User maptoUser(EuUtilisateur utilisateur) {
        User user = new User(utilisateur);
        user.setUserType("user");
        return user;
    }

    public static User mapToUser(EuMembreasso asso) {
        User user = new User();
        user.setId(asso.getMembreassoId());
        user.setImprimer(false);
        user.setNomUser(asso.getMembreassoNom());
        user.setPrenomUser(asso.getMembreassoPrenom());
        user.setPassword(asso.getMembreassoPasse());
        user.setPasswordHash(asso.getMembreassoPasse());
        user.setGroupes(Lists.newArrayList());
        user.setUserType("asso");
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public boolean isImprimer() {
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    public List<String> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<String> groupes) {
        this.groupes = groupes;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
