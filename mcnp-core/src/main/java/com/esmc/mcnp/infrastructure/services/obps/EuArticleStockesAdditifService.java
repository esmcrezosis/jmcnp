package com.esmc.mcnp.infrastructure.services.obps;


import com.esmc.mcnp.domain.entity.obps.EuArticleStockesAdditif;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

/**
 * Created by USER on 12/03/2017.
 */
public interface EuArticleStockesAdditifService extends BaseService<EuArticleStockesAdditif, Long> {

public EuArticleStockesAdditif findArticleStockAdditifByMembreAndReference(String codeMembre, String reference);
}
