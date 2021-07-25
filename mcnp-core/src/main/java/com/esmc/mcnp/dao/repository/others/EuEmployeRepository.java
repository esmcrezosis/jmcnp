package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.others.EuEmploye;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuEmployeRepository extends BaseRepository<EuEmploye, Integer> {

	@Query("select sum(e.montSalaire) From EuEmploye e where e.codeMembreEmployeur like :employeur")
	public Double getSommeSalaireByEmployeur(@Param("employeur") String codeMembre);
}
