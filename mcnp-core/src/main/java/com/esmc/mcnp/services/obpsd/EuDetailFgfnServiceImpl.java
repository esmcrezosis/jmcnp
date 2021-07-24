/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuDetailFgfn;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuDetailFgfnRepository;

/**
 *
 * @author mawuli
 */
@Service("euDetailFgfnService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDetailFgfnServiceImpl extends BaseServiceImpl<EuDetailFgfn, Long> implements EuDetailfgfnService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailFgfnRepository detFgfnRepo;

	@Override
	protected BaseRepository<EuDetailFgfn, Long> getRepository() {
		return detFgfnRepo;
	}

	@Override
	public Long findLastDetFgfnInsertedId() {
		return detFgfnRepo.findLastDetFgfnInsertedId();
	}

	@Override
	public List<EuDetailFgfn> findDetFgFn() {
		return detFgfnRepo.findDetFgFn();
	}

	@Override
	public List<EuDetailFgfn> findDetByFgFn(String codeFgfn) {
		return detFgfnRepo.findDetByFgFn(codeFgfn);
	}

	@Override
	public Double getSumDetByFgFn() {
		return detFgfnRepo.getSumDetByFgFn();
	}

	@Override
	public Double getSumDetByFgFn(String codeFgfn) {
		return detFgfnRepo.getSumDetByFgFn(codeFgfn);
	}

	@Override
	public List<EuDetailFgfn> findByCodeBanque(String codeBanque) {
		return detFgfnRepo.fetchByBanque(codeBanque);
	}

	@Override
	public List<EuDetailFgfn> fetchByBanque(String codeBanque) {
		if (StringUtils.isNotBlank(codeBanque)) {
			return detFgfnRepo.fetchByBanque(codeBanque);
		} else {
			return detFgfnRepo.findDetFgFn();
		}
	}

	@Override
	public Double getSumDetByBanque(String codeBanque) {
		return detFgfnRepo.getSumDetByBanque(codeBanque);
	}

	@Override
	public Double getSumDetByMembre(String codeMembre) {
		return detFgfnRepo.getSumDetByMembre(codeMembre);
	}

	@Override
	public List<EuDetailFgfn> fetchByMembre(String codeMembre) {
		return detFgfnRepo.fetchByMembre(codeMembre);
	}

	@Override
	public Page<EuDetailFgfn> findByMembrePbf(String codeMembre, Pageable page) {
		return detFgfnRepo.findByCodeMembrePbf(codeMembre, page);
	}

	@Override
	public Page<EuDetailFgfn> findByBanque(String codeBanque, Pageable page) {
		return detFgfnRepo.findByCodeBanque(codeBanque, page);
	}

	@Override
	public Page<EuDetailFgfn> findAll(Pageable pageable) {
		return detFgfnRepo.findAll(pageable);
	}

	@Override
	public Page<EuDetailFgfn> findDetByFgfn(String codeFgfn, Pageable pageable) {
		return detFgfnRepo.findDetByFgFn(codeFgfn, pageable);
	}

}
