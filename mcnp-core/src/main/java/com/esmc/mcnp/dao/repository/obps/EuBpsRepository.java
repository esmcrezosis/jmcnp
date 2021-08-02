package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuBps;

public interface EuBpsRepository extends BaseRepository<EuBps, Integer> {
	@Query("select b from EuBps b")
	public List<EuBps> findAllBps();

	public EuBps findByDesignationAndTypeSouscription(String designation, String typesouscription);

	public EuBps findByDesignation(String designation);

}
