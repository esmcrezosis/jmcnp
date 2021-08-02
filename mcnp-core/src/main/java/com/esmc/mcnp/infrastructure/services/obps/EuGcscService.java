package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.domain.entity.smcipn.EuGcsc;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuGcscService extends BaseService<EuGcsc, Long> {

	Long findLastGcscInsertedId();

	EuGcsc findByEuSmcipn_CodeSmcipn(String codeSmcipn);

	EuGcsc findBySmcipnAndBenef(String codeSmcipn, String codeMembre);

	EuGcsc findByEuMembreMorale_CodeMembreMorale(String codemembre);

	EuGcsc findByNumeroAppelOffre(String numeroAppelOffre);
}
