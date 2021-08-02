package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.List;

import com.esmc.mcnp.domain.entity.obpsd.EuGcpPbf;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuGcpPbfService extends BaseService<EuGcpPbf, String> {
	public List<EuGcpPbf> findGcpPbfByCompte(String codeCompte);

	public EuGcpPbf findGcpPbfByCompteAndCapa(String codeCompte, String codeCapa);

	public Double getSumGcpPbfByCompte(String codeCompte);

	public Double getSumGcpPbfByCompteCapa(String codeCompte, String typeCapa);
}
