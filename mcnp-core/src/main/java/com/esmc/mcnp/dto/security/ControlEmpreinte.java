/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.security;

/**
 *
 * @author Administrateur
 */
public class ControlEmpreinte {

    byte[] bTemplate;
    int refTemplateSize;
    
    public ControlEmpreinte() {
    }

    public byte[] getbTemplate() {
        return bTemplate;
    }

    public void setbTemplate(byte[] bTemplate) {
        this.bTemplate = bTemplate;
    }

    public int getRefTemplateSize() {
        return refTemplateSize;
    }

    public void setRefTemplateSize(int refTemplateSize) {
        this.refTemplateSize = refTemplateSize;
    }

 
  

}
