package com.esmc.mcnp.repositories.pc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obpsd.EuBan;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuBanRepository extends BaseRepository<EuBan, Long> {
	List<EuBan> findByCodeMembre(String codeMembre);

	@Query("select b from EuBan b where b.codeMembre like :codeMembre and b.solde > 0")
	List<EuBan> findByMembre(@Param("codeMembre") String codeMembre);
}
