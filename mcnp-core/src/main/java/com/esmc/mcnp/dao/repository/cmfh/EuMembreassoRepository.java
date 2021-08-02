package com.esmc.mcnp.dao.repository.cmfh;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuMembreasso;

public interface EuMembreassoRepository extends BaseRepository<EuMembreasso, Long> {
	EuMembreasso findByMembreassoLogin(String login);
}
