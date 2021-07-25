package com.esmc.mcnp.repositories.cmfh;

import java.util.List;

import com.esmc.mcnp.model.acteur.EuSouscription;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuSouscriptionRepository extends BaseRepository<EuSouscription, Long> {

	List<EuSouscription> findBySouscriptionType(String typeSouscription);
	
	List<EuSouscription> findBySouscriptionProgramme(String programme);
}
