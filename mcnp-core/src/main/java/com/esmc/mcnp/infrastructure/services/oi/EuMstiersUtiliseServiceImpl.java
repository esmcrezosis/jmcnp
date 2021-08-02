package com.esmc.mcnp.infrastructure.services.oi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.odd.EuMstiersUtiliseRepository;
import com.esmc.mcnp.domain.entity.odd.EuMstiersUtilise;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 * Created by mawuli on 07/06/17.
 */
@Service("euMstiersUtiliseService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMstiersUtiliseServiceImpl extends BaseServiceImpl<EuMstiersUtilise, Integer> implements EuMstiersUtiliseService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired
    EuMstiersUtiliseRepository mstiersUtiliseRepository;

    @Override
    protected BaseRepository<EuMstiersUtilise, Integer> getRepository() {
        return mstiersUtiliseRepository;
    }
}
