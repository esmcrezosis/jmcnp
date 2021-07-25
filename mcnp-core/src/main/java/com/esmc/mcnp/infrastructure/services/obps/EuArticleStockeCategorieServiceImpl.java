package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obps.EuArticleStockesCategorie;
import com.esmc.mcnp.repositories.obps.EuArticleStockesCategorieRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("euArticleStockeCategorieService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuArticleStockeCategorieServiceImpl extends BaseServiceImpl<EuArticleStockesCategorie, Long>
		implements EuArticleStockeCategorieService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuArticleStockesCategorieRepository articleStockeCatRepo;

	@Override
	protected BaseRepository<EuArticleStockesCategorie, Long> getRepository() {
		return articleStockeCatRepo;
	}

	@Override
	public Long findLastInsertedRow() {
		return articleStockeCatRepo.findLastInsertedRow();
	}

}
