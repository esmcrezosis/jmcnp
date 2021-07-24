package com.esmc.mcnp.services.ba;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuBonNeutreApproDetail;
import com.esmc.mcnp.model.obpsd.EuBonNeutreApproDetailPK;
import com.esmc.mcnp.repositories.obpsd.EuBonNeutreApproDetailRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("bonNeutreApproDetailService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonNeutreApproDetailServiceImpl extends BaseServiceImpl<EuBonNeutreApproDetail, EuBonNeutreApproDetailPK>
		implements EuBonNeutreApproDetailService {

	private static final long serialVersionUID = 1L;

	private @Autowired EuBonNeutreApproDetailRepository bonApproDetailRepo;

	@Override
	protected BaseRepository<EuBonNeutreApproDetail, EuBonNeutreApproDetailPK> getRepository() {
		return bonApproDetailRepo;
	}

}
