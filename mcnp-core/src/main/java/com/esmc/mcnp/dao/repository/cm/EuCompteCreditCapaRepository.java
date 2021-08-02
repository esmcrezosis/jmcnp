/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapa;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapaPK;

/**
 *
 * @author USER
 */
public interface EuCompteCreditCapaRepository extends BaseRepository<EuCompteCreditCapa, EuCompteCreditCapaPK> {

	@Query("select e from EuCompteCreditCapa e left join fetch e.euCapa c where e.id.idCredit = :idCredit")
	public List<EuCompteCreditCapa> findCreditCapaByIdCredit(@Param("idCredit") Long idCredit);
}
