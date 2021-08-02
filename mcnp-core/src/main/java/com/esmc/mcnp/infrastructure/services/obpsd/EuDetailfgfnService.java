/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.obpsd.EuDetailFgfn;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

/**
 *
 * @author mawuli
 */
public interface EuDetailfgfnService extends BaseService<EuDetailFgfn, Long> {

	public Long findLastDetFgfnInsertedId();

	public List<EuDetailFgfn> findDetFgFn();

	public List<EuDetailFgfn> findDetByFgFn(String codeFgfn);
	
	public Page<EuDetailFgfn> findDetByFgfn(String codeFgfn, Pageable pageable);

	public Double getSumDetByFgFn();

	public Double getSumDetByFgFn(String codeFgfn);

	public Double getSumDetByBanque(String codeBanque);

	public Double getSumDetByMembre(String codeMembre);

	public List<EuDetailFgfn> findByCodeBanque(String codeBanque);

	public List<EuDetailFgfn> fetchByBanque(String codeBanque);

	public List<EuDetailFgfn> fetchByMembre(String codeMembre);

	public Page<EuDetailFgfn> findByMembrePbf(String codeMembre, Pageable page);

	public Page<EuDetailFgfn> findByBanque(String codeBanque, Pageable page);
	
	public Page<EuDetailFgfn> findAll(Pageable pageable);
}
