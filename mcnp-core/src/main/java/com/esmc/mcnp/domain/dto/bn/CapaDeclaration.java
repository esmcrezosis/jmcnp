/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.bn;

import java.io.Serializable;

/**
 *
 * @author Administrateur
 */
public class CapaDeclaration implements Serializable {
    private static final long serialVersionUID = 1L;
    private String referenceMat;
    private String codeMembre;

    public CapaDeclaration() {
    }

    public String getReferenceMat() {
        return referenceMat;
    }

    public void setReferenceMat(String referenceMat) {
        this.referenceMat = referenceMat;
    }

    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }
    
    
}
