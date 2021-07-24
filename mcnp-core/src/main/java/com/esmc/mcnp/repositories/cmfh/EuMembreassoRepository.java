package com.esmc.mcnp.repositories.cmfh;

import com.esmc.mcnp.model.acteur.EuMembreasso;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuMembreassoRepository extends BaseRepository<EuMembreasso, Long> {
	EuMembreasso findByMembreassoLogin(String login);
}
