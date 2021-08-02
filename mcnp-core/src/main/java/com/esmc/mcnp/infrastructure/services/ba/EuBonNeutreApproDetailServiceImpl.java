package com.esmc.mcnp.infrastructure.services.ba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreApproDetailRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreApproDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreApproDetailPK;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
