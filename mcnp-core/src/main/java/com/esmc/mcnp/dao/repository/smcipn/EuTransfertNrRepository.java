package com.esmc.mcnp.repositories.smcipn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.smcipn.EuTransfertNr;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuTransfertNrRepository extends BaseRepository<EuTransfertNr, Long> {

	@Query("select max(t.idTransfertNr) from EuTransfertNr t")
	public Long getLastInsertedId();

	public List<EuTransfertNr> findBycodeMembreBenef(String codeMembre);

	public List<EuTransfertNr> findByCodeMembreTransfert(String codeMembre);

	public Page<EuTransfertNr> findBycodeMembreBenef(String codeMembre, Pageable pageable);

	public Page<EuTransfertNr> findByCodeMembreTransfert(String codeMembre, Pageable pageable);

	public List<EuTransfertNr> findByCodeMembreBenefAndNumAppelOffre(String codeMembre, String numAppelOffre);

	public List<EuTransfertNr> findByCodeMembreTransfertAndNumAppelOffre(String codeMembre, String numAppelOffre);

	public Page<EuTransfertNr> findByCodeMembreBenefAndNumAppelOffre(String codeMembre, String numAppelOffre,
			Pageable pageable);

	public Page<EuTransfertNr> findByCodeMembreTransfertAndNumAppelOffre(String codeMembre, String numAppelOffre,
			Pageable pageable);
}
