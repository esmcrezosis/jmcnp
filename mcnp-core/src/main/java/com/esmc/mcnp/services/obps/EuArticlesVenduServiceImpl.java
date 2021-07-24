package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obps.EuArticlesVendu;
import com.esmc.mcnp.repositories.obps.EuArticlesVenduRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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
