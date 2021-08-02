/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import com.esmc.mcnp.domain.entity.bc.EuPrk;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

/**
 *
 * @author Mawuli
 */
public interface EuPrkService extends BaseService<EuPrk, Integer> {

	/*
	 * public EuPrk findByCreditAndActeur(String typeCredit, Integer typeActeur);
	 * 
	 * public Double findValByCreditAndActeur(String typeCredit, Integer
	 * typeActeur);
	 */

	public List<EuPrk> findValByTegc(String codeTegc);

	public EuPrk findPrkByTegc(String codeTegc);
}
