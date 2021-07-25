package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.model.obpsd.EuEscompte;
import com.esmc.mcnp.services.base.BaseService;

public interface EuEscompteService extends BaseService<EuEscompte, Long> {

	public Long getLastInsertedId();

	public List<EuEscompte> findAllEscompte();

	public List<EuEscompte> findEscompteByMembre(String codeMembre);

	public Double getSommeEscompteByCompte(String codeMembre);

	public List<EuEscompte> findEscompteEchuByCompte(String codeMembre);

	public Double getSommeEscompteEchuByCompte(String codeMembre);
}
