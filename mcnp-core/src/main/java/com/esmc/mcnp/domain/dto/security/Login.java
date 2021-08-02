/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.dto.security;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    private String login;
    private String password;
    private String codeMembre;
    private String codeGroupe;
    private String sousGroupe;
    private Long idUtilisateur;
    private Long idUserParent;
    private String typeUser;

    public Login() {
    }

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Login(String login, String password, String codeMembre, String codeGroupe, Long idUtilisateur) {
        this.login = login;
        this.password = password;
        this.codeMembre = codeMembre;
        this.codeGroupe = codeGroupe;
        this.idUtilisateur = idUtilisateur;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public String getSousGroupe() {
        return sousGroupe;
    }

    public void setSousGroupe(String sousGroupe) {
        this.sousGroupe = sousGroupe;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Long getIdUserParent() {
        return idUserParent;
    }

    public void setIdUserParent(Long id_user_parent) {
        this.idUserParent = id_user_parent;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

}
