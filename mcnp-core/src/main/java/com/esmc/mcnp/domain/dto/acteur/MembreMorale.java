/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.dto.acteur;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.esmc.mcnp.domain.dto.ksu.Compte;

/**
 * @author Mawuli
 */
@Data
@Accessors(chain = true)
public class MembreMorale implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codeMembreMorale;
    private String raisonSociale;
    private String DomaineActivite;
    private String NumRegistreMembre;
    private String PortableMembre;
    private String EmailMembre;
    private String SiteWeb;
    private String QuartierMembre;
    private String VilleMembre;
    private Long idUtilisateur;
    private Long idFiliere;
    private List<Membre> representants;
    private List<Compte> comptes;

    public MembreMorale() {
    }

    public MembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }

    public MembreMorale(String codeMembreMorale, String raisonSociale) {
        this.codeMembreMorale = codeMembreMorale;
        this.raisonSociale = raisonSociale;
    }

    public MembreMorale(String codeMembreMorale, String raisonSociale, Long idUtilisateur, List<Membre> representants) {
        this.codeMembreMorale = codeMembreMorale;
        this.raisonSociale = raisonSociale;
        this.idUtilisateur = idUtilisateur;
        this.representants = representants;
    }

    public List<Membre> getRepresentants() {
        if (representants == null) {
            representants = new ArrayList<>();
        }
        return representants;
    }

}
