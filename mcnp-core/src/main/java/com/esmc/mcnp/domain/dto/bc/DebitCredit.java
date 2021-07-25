/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dto.bc;

import java.io.Serializable;

/**
 *
 * @author TOSHIBA
 */
@SuppressWarnings("serial")
public class DebitCredit implements Serializable{
    
    private String codeCompte;
    private double montantTransferer;
    private String typeDebitCredit;
    private String typeRecurrent;
    private String codeProduit;

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

   
    public double getMontantTransferer() {
        return montantTransferer;
    }

    public void setMontantTransferer(double montantTransferer) {
        this.montantTransferer = montantTransferer;
    }

    public String getTypeDebitCredit() {
        return typeDebitCredit;
    }

    public void setTypeDebitCredit(String typeDebitCredit) {
        this.typeDebitCredit = typeDebitCredit;
    }

    public String getTypeRecurrent() {
        return typeRecurrent;
    }

    public void setTypeRecurrent(String typeRecurrent) {
        this.typeRecurrent = typeRecurrent;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

   
}
