package com.esmc.mcnp.dao.repository.cmfh;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuSouscription;

public interface EuSouscriptionRepository extends BaseRepository<EuSouscription, Long> {

	List<EuSouscription> findBySouscriptionType(String typeSouscription);
	
	List<EuSouscription> findBySouscriptionProgramme(String programme);
}
