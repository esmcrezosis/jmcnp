package com.esmc.mcnp.infrastructure.services.oi;

import java.util.List;

import com.esmc.mcnp.domain.entity.odd.EuMstierListebc;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuMstierListebcService extends BaseService<EuMstierListebc, Integer> {

	public List<EuMstierListebc> findByStatut();

	public List<EuMstierListebc> findByStatut(Integer statut);

	public List<EuMstierListebc> findByBenef(String codeMembre);

	public List<EuMstierListebc> findByBenefAndOdd(String codeMembre, Integer idOdd);
	
	EuMstierListebc findByCodeFicheOdd(String codeFicheOdd);
}
