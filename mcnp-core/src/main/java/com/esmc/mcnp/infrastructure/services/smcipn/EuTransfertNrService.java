package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.smcipn.EuTransfertNr;

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
