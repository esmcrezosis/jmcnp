package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuArticlesVenduRepository;
import com.esmc.mcnp.domain.entity.obps.EuArticlesVendu;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

/**
 * Created by USER on 12/03/2017.
 */
@Service("euArticlesVenduService")
@Transactional(propagation = Propagation.REQUIRED)
public class EuArticlesVenduServiceImpl extends BaseServiceImpl<EuArticlesVendu, Long> implements EuArticlesVenduService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuArticlesVenduRepository articlesVenduRepository;

    @Override
    protected BaseRepository<EuArticlesVendu, Long> getRepository() {
        return articlesVenduRepository;
    }
}
