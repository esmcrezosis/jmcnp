package com.esmc.mcnp.infrastructure.services.obps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuArticleStockesAdditifRepository;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockesAdditif;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euArticleStockesAdditifService")
@Transactional(propagation = Propagation.REQUIRED)
public class EuArticleStockesAdditifServiceImpl extends BaseServiceImpl<EuArticleStockesAdditif, Long> implements EuArticleStockesAdditifService {

	private static final long serialVersionUID = 1L;
	
	private @Autowired EuArticleStockesAdditifRepository articleStockesAdditifRepository;

    @Override
    protected BaseRepository<EuArticleStockesAdditif, Long> getRepository() {
        return articleStockesAdditifRepository;
    }

	@Override
	public EuArticleStockesAdditif findArticleStockAdditifByMembreAndReference(String codeMembre, String reference) {
		// TODO Auto-generated method stub
		return articleStockesAdditifRepository.findArticleStockesAdditifByMembreAndReference(codeMembre, reference);
	}

    
	
    
    
}
