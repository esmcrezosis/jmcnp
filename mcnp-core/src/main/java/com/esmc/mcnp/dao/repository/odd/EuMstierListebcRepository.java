package com.esmc.mcnp.dao.repository.odd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.odd.EuMstierListebc;

public interface EuMstierListebcRepository extends BaseRepository<EuMstierListebc, Integer> {

	EuMstierListebc findByCodeFicheOdd(String codeFicheOdd);
	
	public List<EuMstierListebc> findByStatut(Integer statut);
	
	public List<EuMstierListebc> findByCodeMembreBenef(String codeMembre);
	
	public List<EuMstierListebc> findByCodeMembreBenefAndIdOdd(String codeMembre, Integer idOdd);

	@Query("select m from EuMstierListebc m where m.statut = 0 or m.statut = 1")
	public List<EuMstierListebc> findListeByStatut();
}
