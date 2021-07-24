package com.esmc.mcnp.services.obps;


import com.esmc.mcnp.model.obps.EuArticleStockesAdditif;
import com.esmc.mcnp.services.base.BaseService;

/**
 * Created by USER on 12/03/2017.
 */
public interface EuArticleStockesAdditifService extends BaseService<EuArticleStockesAdditif, Long> {

public EuArticleStockesAdditif findArticleStockAdditifByMembreAndReference(String codeMembre, String reference);
}
