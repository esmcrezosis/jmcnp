package com.esmc.mcnp.dao.repository.obps;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuDetailBonOpi;

public interface EuDetailBonOpiRepository extends BaseRepository<EuDetailBonOpi, Integer> {

	@Query("select max(d.idDetailBonOpi) From EuDetailBonOpi d")
	public Integer getLastInsertedId();

	public EuDetailBonOpi findByEuBon_BonNumero(String numeroBon);
	
	public EuDetailBonOpi findByIdTpagcp(Long idtpagcp);
}
