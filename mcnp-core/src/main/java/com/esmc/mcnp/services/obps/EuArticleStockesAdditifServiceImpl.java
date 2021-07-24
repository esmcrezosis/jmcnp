package com.esmc.mcnp.services.obps;
import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obps.EuArticleStockesAdditif;
import com.esmc.mcnp.repositories.obps.EuArticleStockesAdditifRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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
