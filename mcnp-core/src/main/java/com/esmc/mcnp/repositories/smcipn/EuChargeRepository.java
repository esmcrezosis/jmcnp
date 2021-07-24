/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.smcipn;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.pc.EuCharge;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuChargeRepository extends BaseRepository<EuCharge, Integer> {

	List<EuCharge> findByEuTypeCharge_CodeTypeCharge(String codeType);

	List<EuCharge> findByEuTypeCharge_IdTypeCharge(Integer idTypecharge);

	@Query("select c From EuCharge c where c.codeCharge like :code")
	Optional<EuCharge> findByCodeCharge(@Param("code") String codeCharge);
}
