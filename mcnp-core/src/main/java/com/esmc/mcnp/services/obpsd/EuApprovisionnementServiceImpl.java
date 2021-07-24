package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuApprovisionnement;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuApprovisionnementRepository;

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
