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

import com.esmc.mcnp.model.obpsd.EuFgfn;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuFgfnRepository extends BaseRepository<EuFgfn, String> {

	@Query("select f from EuFgfn f where f.soldeFgfn > 0")
	public List<EuFgfn> findAllFgFn();

	@Query("select f From EuFgfn f left join f.euMembreMorale")
	public Page<EuFgfn> findAllFgfn(Pageable pageable);

	@Query("select sum(f.soldeFgfn) from EuFgfn f where f.soldeFgfn > 0")
	public Double getSommeFgfn();

	public EuFgfn findByEuMembreMorale_CodeMembreMorale(String codeMembre);

	public EuFgfn findByCodeBanque(String codeBanque);

	@Query("select f From EuFgfn f where f.euMembreMorale.codeMembreMorale like :code")
	public Page<EuFgfn> findByMembre(@Param("code") String codeMembre, Pageable pageable);

	public Page<EuFgfn> findByCodeBanque(String codeBanque, Pageable pageable);

}
