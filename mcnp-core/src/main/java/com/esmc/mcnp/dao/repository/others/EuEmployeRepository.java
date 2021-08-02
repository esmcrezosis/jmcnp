package com.esmc.mcnp.dao.repository.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.others.EuEmploye;

public interface EuEmployeRepository extends BaseRepository<EuEmploye, Integer> {

	@Query("select sum(e.montSalaire) From EuEmploye e where e.codeMembreEmployeur like :employeur")
	public Double getSommeSalaireByEmployeur(@Param("employeur") String codeMembre);
}
