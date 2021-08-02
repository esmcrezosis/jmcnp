package com.esmc.mcnp.infrastructure.services.common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.others.EuProcurationRepository;
import com.esmc.mcnp.domain.entity.others.EuProcuration;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
