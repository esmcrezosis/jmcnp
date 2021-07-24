package com.esmc.mcnp.services.oi;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.odd.EuMstiersUtilise;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.odd.EuMstiersUtiliseRepository;

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
