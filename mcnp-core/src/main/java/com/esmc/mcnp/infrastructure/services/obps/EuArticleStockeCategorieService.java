package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.domain.entity.obps.EuArticleStockesCategorie;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuArticleStockeCategorieService extends BaseService<EuArticleStockesCategorie, Long> {
   
	Long findLastInsertedRow();
}
