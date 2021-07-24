/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.components;

import com.esmc.mcnp.dto.smcipn.Transfert;

/**
 *
 * @author USER
 */
public interface TransfertCreditService {

    public String transfertCredit(Transfert transfert);

    public String transfertCNCS(Transfert transfert);
    
    public String transfertNr(Transfert transfert);
}
