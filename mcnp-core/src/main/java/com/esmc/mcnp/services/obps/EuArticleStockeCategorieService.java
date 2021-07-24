package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.model.obps.EuArticleStockesCategorie;
import com.esmc.mcnp.services.base.BaseService;

public interface EuArticleStockeCategorieService extends BaseService<EuArticleStockesCategorie, Long> {
   
	Long findLastInsertedRow();
}
