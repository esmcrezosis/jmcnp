package com.esmc.mcnp.dao.repository.cmfh;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf107;

public interface EuRepartitionMf107Repository extends BaseRepository<EuRepartitionMf107, Long> {
	List<EuRepartitionMf107> findByCodeMembre(String codeMembre);

	List<EuRepartitionMf107> findByIdMf107(Long idMf107);

	Page<EuRepartitionMf107> findByCodeMembre(String codeMembre, Pageable pageable);

	Page<EuRepartitionMf107> findByIdMf107(Long idMf107, Pageable pageable);

	@Query("select sum(rm.montReglt) from EuRepartitionMf107 rm where rm.codeMembre like :membre")
	Double getSumMf107MontRegltByMembre(@Param("membre") String codeMembre);

	@Query("select sum(rm.montRep) from EuRepartitionMf107 rm where rm.codeMembre like :membre")
	Double getSumMf107MontRepByMembre(@Param("membre") String codeMembre);
	
	@Query("select sum(rm.soldeRep) from EuRepartitionMf107 rm where rm.codeMembre like :membre")
	Double getSumMf107SoldeRepByMembre(@Param("membre") String codeMembre);
}
