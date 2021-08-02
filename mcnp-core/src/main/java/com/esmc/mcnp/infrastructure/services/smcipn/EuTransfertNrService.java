package com.esmc.mcnp.infrastructure.services.smcipn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.smcipn.EuTransfertNr;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuTransfertNrService extends BaseService<EuTransfertNr, Long> {
	public Long getLastInsertedId();

	public List<EuTransfertNr> findByBenef(String codeMembre);

	public List<EuTransfertNr> findbyTransfert(String codeMembre);

	public List<EuTransfertNr> findByBenefAndAppelOffre(String codeMembre, String numAppelOffre);

	public List<EuTransfertNr> findByTransfertAndAppelOffre(String codeMembre, String numAppelOffre);

	public Page<EuTransfertNr> findByBenef(String codeMembre, Pageable pageable);

	public Page<EuTransfertNr> findbyTransfert(String codeMembre, Pageable pageable);

	public Page<EuTransfertNr> findByBenefAndAppelOffre(String codeMembre, String numAppelOffre, Pageable pageable);

	public Page<EuTransfertNr> findByTransfertAndAppelOffre(String codeMembre, String numAppelOffre, Pageable pageable);
}
