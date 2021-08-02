package com.esmc.mcnp.dao.repository.acteurs;

import java.util.List;
import java.util.Optional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuEli;

public interface EuEliRepository extends BaseRepository<EuEli, Integer> {
	Optional<EuEli> findByNumeroEli(String numeroEli);

	List<EuEli> findByCodeTegc(String codeTegc);
}
