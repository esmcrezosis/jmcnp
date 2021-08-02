/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.domain.dto.other;

import java.io.Serializable;

/**
 *
 * @author Administrateur
 */
@SuppressWarnings("serial")
public class CrudObjet implements Serializable {
   private Object  objet;
   private String action;

    public CrudObjet() {
    }
   
   

    public Object getObjet() {
        return objet;
    }

    public void setObjet(Object objet) {
        this.objet = objet;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
   
   
}
