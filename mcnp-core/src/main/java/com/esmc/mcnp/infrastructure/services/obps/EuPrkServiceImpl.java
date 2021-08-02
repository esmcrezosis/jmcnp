/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuPrkRepository;
import com.esmc.mcnp.domain.entity.bc.EuPrk;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
