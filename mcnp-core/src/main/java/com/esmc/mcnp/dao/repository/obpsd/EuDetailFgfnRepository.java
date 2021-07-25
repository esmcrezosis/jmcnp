/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.obpsd;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obpsd.EuDetailFgfn;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuDetailFgfnRepository
		extends BaseRepository<EuDetailFgfn, Long> {

	@Query("select max(d.idFgfn) from EuDetailFgfn d")
	public Long findLastDetFgfnInsertedId();

	public Page<EuDetailFgfn> findByCodeBanque(String codeBanque, Pageable page);

	public Page<EuDetailFgfn> findByCodeMembrePbf(String codeMembrePbf, Pageable page);

	@Query("select d from EuDetailFgfn d join d.euFgfn f where d.soldeFgfn > 0 and d.codeBanque like :codeBanque")
	public List<EuDetailFgfn> findByBanque(@Param("codeBanque") String codeBanque, Pageable page);

	@Query("select d from EuDetailFgfn d join d.euFgfn f where d.soldeFgfn > 0 and d.codeMembrePbf like :codeMembre")
	public List<EuDetailFgfn> findByMembrePbf(@Param("codeMembre") String codeMembrePbf, Pageable page);

	@Query("select d from EuDetailFgfn d where d.soldeFgfn > 0")
	public List<EuDetailFgfn> findDetFgFn();

	@Query("select d from EuDetailFgfn d join d.euFgfn f where d.soldeFgfn > 0 and f.codeFgfn like :codeFgfn")
	public List<EuDetailFgfn> findDetByFgFn(@Param("codeFgfn") String codeFgfn);
	
	@Query("select d from EuDetailFgfn d join d.euFgfn f where d.soldeFgfn > 0 and f.codeFgfn like :codeFgfn")
	public Page<EuDetailFgfn> findDetByFgFn(@Param("codeFgfn") String codeFgfn, Pageable pageable);

	@Query("select sum(d.soldeFgfn) from EuDetailFgfn d where d.soldeFgfn > 0")
	public Double getSumDetByFgFn();

	@Query("select sum(d.soldeFgfn) from EuDetailFgfn d join d.euFgfn f where d.soldeFgfn > 0 and f.codeFgfn like :codefgfn")
	public Double getSumDetByFgFn(@Param("codefgfn") String codeFgfn);

	@Query("select sum(d.soldeFgfn) from EuDetailFgfn d join d.euFgfn f where d.soldeFgfn > 0 and d.codeBanque like :codebanque")
	public Double getSumDetByBanque(@Param("codebanque") String codeBanque);

	@Query("select sum(d.soldeFgfn) from EuDetailFgfn d join d.euFgfn f where d.soldeFgfn > 0 and d.codeMembrePbf like :codeMembre")
	public Double getSumDetByMembre(@Param("codeMembre") String codeMembre);

	public List<EuDetailFgfn> findByCodeBanque(String codeBanque);

	@Query("select d from EuDetailFgfn d where d.soldeFgfn > 0 and d.codeBanque like :codebanque")
	public List<EuDetailFgfn> fetchByBanque(@Param("codebanque") String codeBanque);

	@Query("select d from EuDetailFgfn d where d.soldeFgfn > 0 and d.codeMembrePbf like :codeMembre")
	public List<EuDetailFgfn> fetchByMembre(@Param("codeMembre") String codeMembre);
}
