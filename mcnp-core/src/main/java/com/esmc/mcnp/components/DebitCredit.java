/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.components;

/**
 *
 * @author Administrateur
 */
public interface DebitCredit {

    public String OperationDebitCredit(String codecompte, double montantATransferer, String TypedebitCredit, String TypeRecurrent, String produit);

    public String OperationDebitCreditNoir(String codecompte, double montantATransferer, String codeProduit);

}
