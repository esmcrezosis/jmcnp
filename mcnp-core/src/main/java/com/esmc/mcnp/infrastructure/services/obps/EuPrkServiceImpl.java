/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.bc.EuPrk;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuPrkRepository;

/**
 *
 * @author Mawuli
 */
@Service("prkService")
@Transactional
public class EuPrkServiceImpl extends BaseServiceImpl<EuPrk, Integer> implements EuPrkService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EuPrkRepository prkRepo;

	@Override
	protected BaseRepository<EuPrk, Integer> getRepository() {
		return prkRepo;
	}

	@Override
	public List<EuPrk> findValByTegc(String codeTegc) {
		return prkRepo.findByEuTegc_CodeTegc(codeTegc);
	}

	@Override
	public EuPrk findPrkByTegc(String codeTegc) {
		return prkRepo.findByCodeTegc(codeTegc);
	}

	/*
	 * @Override public EuPrk findByCreditAndActeur(String typeCredit, Integer
	 * typeActeur) { return prkRepo.findByActeurAndCredit(typeCredit, typeActeur); }
	 * 
	 * @Override public Double findValByCreditAndActeur(String typeCredit, Integer
	 * typeActeur) { return prkRepo.findPrkByActeurAndCredit(typeCredit,
	 * typeActeur); }
	 */

}
