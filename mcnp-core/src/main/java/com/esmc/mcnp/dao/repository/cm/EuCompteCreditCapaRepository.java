/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.cm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuCompteCreditCapaRepository extends BaseRepository<EuCompteCreditCapa, EuCompteCreditCapaPK> {

	@Query("select e from EuCompteCreditCapa e left join fetch e.euCapa c where e.id.idCredit = :idCredit")
	public List<EuCompteCreditCapa> findCreditCapaByIdCredit(@Param("idCredit") Long idCredit);
}
