/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.components;

import com.esmc.mcnp.domain.dto.bn.TransfertSMS;
import com.esmc.mcnp.domain.dto.smcipn.Transfert;

/**
 *
 * @author mawuli
 */
public interface TransfertService {

	public String doTransfertGros(TransfertSMS transfert);

	public String doTransfertSource(TransfertSMS transfert);

	public String doTransfertDetail(TransfertSMS transfert);

	public String doTransfertCmfh(TransfertSMS transfert);

	public String doTransfertPre(Transfert transfert);

	public String doTransfertPreRes(Transfert transfert);

	public String doTransfertDetail(String type, double montant);
}
