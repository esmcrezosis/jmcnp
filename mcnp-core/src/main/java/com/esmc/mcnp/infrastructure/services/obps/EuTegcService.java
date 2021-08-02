package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.dto.obps.TegcView;
import com.esmc.mcnp.domain.entity.obps.EuTegc;
import com.esmc.mcnp.infrastructure.services.base.BaseService;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface EuTegcService extends CrudService<EuTegc, String> {

	EuTegc findByCodeTegc(String codeTegc);

	EuTegc creerTe(String codeMembre);

	public List<EuTegc> findByCodeMembre(String codeMembre);
	
	public Double getSoldeByCodeMembre(String codeMembre);

	public List<TegcView> findbyMembre(String codeMembre);

	public Page<EuTegc> findByMembre(String codeMembre, Pageable pageable);

	public List<EuTegc> findTebyMembre(String codeMembre);

	public Double getSoldeByMembreAndTe(String codeMembre, String codeTegc);

	public List<EuTegc> findByMembreAndTe(String codeMembre, String codeTegc);

	public EuTegc findTegcByCodeMembreAndNomTegc(String codeMembre, String nomTegc);

	public List<EuTegc> findByCodeMembrePhysique(String codeMembre);

}
