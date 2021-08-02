package com.esmc.mcnp.dao.repository.cmfh;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf11000;

public interface EuRepartitionMf11000Repository extends BaseRepository<EuRepartitionMf11000, Long> {
	
	List<EuRepartitionMf11000> findByCodeMembre(String codeMembre);
	
	List<EuRepartitionMf11000> findByCodeMf11000(String codeMf11000);

	List<EuRepartitionMf11000> findByIdMf11000(Long idMf11000);

	Page<EuRepartitionMf11000> findByCodeMembre(String codeMembre, Pageable pageable);

	Page<EuRepartitionMf11000> findByCodeMf11000(String codeMf11000, Pageable pageable);

	Page<EuRepartitionMf11000> findByIdMf11000(Long idMf11000, Pageable pageable);

	@Query("select sum(rm.montReglt) from EuRepartitionMf11000 rm where rm.codeMembre like :membre")
	Double getSumMf11000MontRegltByMembre(@Param("membre") String codeMembre);

	@Query("select sum(rm.montRep) from EuRepartitionMf11000 rm where rm.codeMembre like :membre")
	Double getSumMf11000MontRepByMembre(@Param("membre") String codeMembre);
	
	@Query("select sum(rm.soldeRep) from EuRepartitionMf11000 rm where rm.codeMembre like :membre")
	Double getSumMf11000SoldeRepByMembre(@Param("membre") String codeMembre);
	
	@Query("select sum(rm.montReglt) from EuRepartitionMf11000 rm where rm.codeMf11000 like :membre")
	Double getSumMf11000MontRegltByCode(@Param("membre") String codeMembre);

	@Query("select sum(rm.montRep) from EuRepartitionMf11000 rm where rm.codeMf11000 like :membre")
	Double getSumMf11000MontRepByCode(@Param("membre") String codeMembre);
	
	@Query("select sum(rm.soldeRep) from EuRepartitionMf11000 rm where rm.codeMf11000 like :membre")
	Double getSumMf11000SoldeRepByCode(@Param("membre") String codeMembre);
}
