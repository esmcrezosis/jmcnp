package com.esmc.mcnp.services.common;
import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.others.EuProcuration;
import com.esmc.mcnp.repositories.others.EuProcurationRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("euProcurationService")
@Transactional(propagation = Propagation.REQUIRED)
public class EuProcurationServiceImpl extends BaseServiceImpl<EuProcuration, Integer> implements EuProcurationService {

	private static final long serialVersionUID = 1L;
	
	private @Autowired EuProcurationRepository procurationRepository;

    @Override
    protected BaseRepository<EuProcuration, Integer> getRepository() {
        return procurationRepository;
    }

	@Override
	public EuProcuration findProcurationByCodeMembre(String codeMembre) {
		return procurationRepository.findProcurationByCodeMembre(codeMembre);
	}

	
    
}
