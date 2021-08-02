package com.esmc.mcnp.infrastructure.services.smcipn;

import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.others.EuProposition;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuAppelOffreService extends BaseService<EuAppelOffre, Long> {

	public Long getLastinsertedId();

	public EuProposition findPropositionByAppelOffre(String numeroOffre);

	public EuAppelOffre findAppelOffresByNumero(String numeroOffre);

	public EuProposition findPropositionByNumero(String numeroOffre);
}
