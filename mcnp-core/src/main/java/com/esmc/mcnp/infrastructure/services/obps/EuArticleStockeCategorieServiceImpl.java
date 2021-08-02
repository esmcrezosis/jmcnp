package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuArticleStockesCategorieRepository;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockesCategorie;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
