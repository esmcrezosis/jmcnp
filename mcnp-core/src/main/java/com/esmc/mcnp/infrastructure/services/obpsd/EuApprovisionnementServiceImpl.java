package com.esmc.mcnp.infrastructure.services.obpsd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuApprovisionnementRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuApprovisionnement;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euApprovisionnementService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class EuApprovisionnementServiceImpl extends BaseServiceImpl<EuApprovisionnement, Long> implements EuApprovisionnementService {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EuApprovisionnementRepository approRepo;

    @Override
    protected BaseRepository<EuApprovisionnement, Long> getRepository() {
        return approRepo;
    }
}
