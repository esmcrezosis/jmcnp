package com.esmc.mcnp.services.bc;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.bc.EuCnp;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.bc.EuDomiciliation;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCnpService extends BaseService<EuCnp, Long> {
	Long getLastCnpInsertedId();

	EuCnp findBySourceCredit(Long id, String source);
	
	EuCnp findCnpBySourceCredit(Long id, String source);

	List<EuCnp> findByIdCredit(Long idCredit);

	EuCnp findByIdGcp(Long id);

	void createCnp(EuCompteCredit cc, EuCapa capa, EuDomiciliation domiciliation, String source, Date date, double mont_credit);

	void updateCnp(EuCnp cnp, double mont_credit);

    void updateCnp(Long idCredit, String source, double mont_credit);
}
