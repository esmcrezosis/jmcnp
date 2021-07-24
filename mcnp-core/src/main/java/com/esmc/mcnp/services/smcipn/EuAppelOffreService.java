package com.esmc.mcnp.services.smcipn;

import com.esmc.mcnp.model.op.EuAppelOffre;
import com.esmc.mcnp.model.others.EuProposition;
import com.esmc.mcnp.services.base.BaseService;

public interface EuAppelOffreService extends BaseService<EuAppelOffre, Long> {

	public Long getLastinsertedId();

	public EuProposition findPropositionByAppelOffre(String numeroOffre);

	public EuAppelOffre findAppelOffresByNumero(String numeroOffre);

	public EuProposition findPropositionByNumero(String numeroOffre);
}
