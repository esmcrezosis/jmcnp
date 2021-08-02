/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.oi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.oi.EuCaps;

/**
 *
 * @author USER
 */
public interface EuCapsRepository extends BaseRepository<EuCaps, String> {

	public EuCaps findByEuMembre2_CodeMembre(String codeMembre);

	@Query("select c from EuCaps c join fetch c.euMembre2 m2 left join fetch c.euMembreMorale mm left join fetch c.euMembre1 m where m2.codeMembre like :code")
	public EuCaps findByCodeMembreBenef(@Param("code") String codeMembre);
}
