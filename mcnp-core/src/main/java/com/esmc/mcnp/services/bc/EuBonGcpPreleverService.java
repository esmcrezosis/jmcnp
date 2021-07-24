package com.esmc.mcnp.services.bc;

import java.util.List;

import com.esmc.mcnp.model.obps.EuBonGcpPrelever;
import com.esmc.mcnp.model.obps.EuBonGcpPreleverPK;
import com.esmc.mcnp.services.base.BaseService;

public interface EuBonGcpPreleverService extends BaseService<EuBonGcpPrelever, EuBonGcpPreleverPK> {

	public List<EuBonGcpPrelever> findByBonId(Integer id);

	public List<EuBonGcpPrelever> findByBonNumero(String numero);

	Integer countTpagcpByBonNumero(String numero);

	List<Long> findIdTpagcpByBonNumero(String numero);

	List<EuBonGcpPrelever> findByIdTpagcp(Long idTpagcp);
}
