package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.obps.EuBps;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuBpsRepository extends BaseRepository<EuBps, Integer> {
	@Query("select b from EuBps b")
	public List<EuBps> findAllBps();

	public EuBps findByDesignationAndTypeSouscription(String designation, String typesouscription);

	public EuBps findByDesignation(String designation);

}
