package com.esmc.mcnp.services.pc;

import java.util.List;

import com.esmc.mcnp.model.obpsd.EuBan;
import com.esmc.mcnp.services.base.BaseService;

public interface EuBanService extends BaseService<EuBan, Long> {

	int emettreBan(String codeMembre, double montant);

	EuBan emettreBanBis(String codeMembre, double montant);

	List<EuBan> findByCodeMembre(String codeMembre);

	List<EuBan> findByMembre(String codeMembre);
}
