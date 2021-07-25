package com.esmc.mcnp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.esmc.mcnp.model.security.EuUserGroup;
import com.esmc.mcnp.model.security.EuUtilisateur;

public class UserSecurity extends EuUtilisateur implements UserDetails, CredentialsContainer, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserSecurity(EuUtilisateur user) {
        if (user != null) {
            this.setIdUtilisateur(user.getIdUtilisateur());
            this.setIdFiliere(user.getIdFiliere());
            this.setIdPays(user.getIdPays());
            this.setIdUtilisateurParent(user.getIdUtilisateurParent());
            this.setNomUtilisateur(user.getNomUtilisateur());
            this.setPrenomUtilisateur(user.getPrenomUtilisateur());
            this.setLogin(user.getLogin());
            this.setPwd(user.getPwd());
            this.setPasswordHash(user.getPasswordHash());
            this.setCodeActeur(user.getCodeActeur());
            this.setCodeGacFiliere(user.getCodeGacFiliere());
            this.setCodeGroupeCreate(user.getCodeGroupeCreate());
            this.setCodeMembre(user.getCodeMembre());
            this.setCodePasse(user.getCodePasse());
            this.setCodeZone(user.getCodeZone());
            this.setUlock(user.getUlock());
            this.setCodeTegc(user.getCodeTegc());
            this.setRole(user.getRole());
            this.setSection(user.getSection());
            this.setOdd(user.getOdd());
            this.setCible(user.getCible());
            this.setIndicateur(user.getIndicateur());
            this.setConnecte(user.getConnecte());
            this.setChPwdFlog(user.getChPwdFlog());
            this.setEuAgence(user.getEuAgence());
            this.setEuSecteur(user.getEuSecteur());
            this.setEuUserGroup(user.getEuUserGroup());
            this.setAgencesOdd(user.getAgencesOdd());
            this.setCentre(user.getCentre());
            this.setPermissions(user.getPermissions());
        }
    }

    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @Override
    public void eraseCredentials() {
        this.setPwd(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        if (Objects.nonNull(this.getEuUserGroup())) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.getEuUserGroup().getCodeGroupe());
            roles.add(authority);
        }
        if (Objects.nonNull(this.getPermissions())) {
            this.getPermissions().forEach(p -> {
                System.out.println("Role: " + p.getEuRole().getCodeRoles());
                roles.add(new SimpleGrantedAuthority(p.getEuRole().getCodeRoles()));
            });
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return this.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    protected UserSecurity clone() {
        try {
            return (UserSecurity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // not possible
        }
    }

}
