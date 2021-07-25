package com.esmc.mcnp.repositories.obps;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuDetailBonOpiRepository extends BaseRepository<EuDetailBonOpi, Integer> {

	@Query("select max(d.idDetailBonOpi) From EuDetailBonOpi d")
	public Integer getLastInsertedId();

	public EuDetailBonOpi findByEuBon_BonNumero(String numeroBon);
	
	public EuDetailBonOpi findByIdTpagcp(Long idtpagcp);
}
