/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.infrastructure.services.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.bc.EuTypeCreditRepository;
import com.esmc.mcnp.domain.entity.bc.EuTypeCredit;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 *
 * @author USER
 */
@Service("typeCredit")
@Transactional
public class EuTypeCreditServiceImpl extends BaseServiceImpl<EuTypeCredit, String> implements EuTypeCreditService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EuTypeCreditRepository typeCreditDao;

	@Override
	protected BaseRepository<EuTypeCredit, String> getRepository() {
		return typeCreditDao;
	}

	@Override
	public List<EuTypeCredit> findByProduit(String typeProduit) {
		return typeCreditDao.findByTypeProduit(typeProduit);
	}

	@Override
	public List<EuTypeCredit> fetchWithoutSalaire() {
		return typeCreditDao.fetchWithoutSalaire();
	}

	@Override
	public Double findQuotaRByTypeCredit(String codeTypeCredit) {
		return typeCreditDao.findQuotaRByTypeCredit(codeTypeCredit);
	}

	@Override
	public Double findQuotaNRByTypeCredit(String codeTypeCredit) {
		return typeCreditDao.findQuotaNRByTypeCredit(codeTypeCredit);
	}
	
	
	

}
