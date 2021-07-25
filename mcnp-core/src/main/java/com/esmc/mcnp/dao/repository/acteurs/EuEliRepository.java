package com.esmc.mcnp.repositories.acteurs;

import java.util.List;
import java.util.Optional;

import com.esmc.mcnp.model.acteur.EuEli;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuEliRepository extends BaseRepository<EuEli, Integer> {
	Optional<EuEli> findByNumeroEli(String numeroEli);

	List<EuEli> findByCodeTegc(String codeTegc);
}
