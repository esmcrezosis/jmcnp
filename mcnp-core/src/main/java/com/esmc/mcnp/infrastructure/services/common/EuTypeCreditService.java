/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.services.common;

import java.util.List;

import com.esmc.mcnp.model.bc.EuTypeCredit;
import com.esmc.mcnp.services.base.BaseService;

/**
 *
 * @author USER
 */
public interface EuTypeCreditService extends BaseService<EuTypeCredit, String> {

	public List<EuTypeCredit> findByProduit(String typeProduit);
	
	public List<EuTypeCredit> fetchWithoutSalaire();
	
	public Double findQuotaRByTypeCredit(String codeTypeCredit);

	public Double findQuotaNRByTypeCredit(String codeTypeCredit);
}
