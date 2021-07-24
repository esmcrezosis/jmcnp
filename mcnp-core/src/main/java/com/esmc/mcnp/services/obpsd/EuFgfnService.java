package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.model.acteur.EuEli;
import com.esmc.mcnp.model.obpsd.EuFgfn;
import com.esmc.mcnp.model.obpsd.EuTraite;

public interface EuFgfnService extends BaseService<EuFgfn, String> {
	public List<EuFgfn> findAllFgFn();

	public Double getSommeFgfn();

	public EuFgfn findByMembre(String codeMembre);

	public void createFgfnByBonNeutreDetail(EuBonNeutreDetail bnDetail, String codeSms, double montant);

	public void createFgfn(String codeBanque, String codeSms, EuEli eli, EuTraite traite, double montant);

	public Page<EuFgfn> findAll(Pageable pageable);

	public Page<EuFgfn> findByMembre(String codeMembre, Pageable pageable);

	public Page<EuFgfn> findByBanque(String codeBanque, Pageable pageable);
}
