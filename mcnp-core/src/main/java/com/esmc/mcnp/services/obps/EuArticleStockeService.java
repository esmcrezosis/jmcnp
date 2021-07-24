package com.esmc.mcnp.services.obps;

import java.util.List;
import com.esmc.mcnp.model.obps.EuArticleStockes;
import com.esmc.mcnp.services.base.BaseService;

/**
 * Created by USER on 12/03/2017.
 */
public interface EuArticleStockeService extends BaseService<EuArticleStockes, Long> {

	Long findMaxId();

	public EuArticleStockes findArticleStockeByCodebarre(String codeBarre);

	public List<String> findReferenceByCategorie(Integer articleStockesCategorie);

	public EuArticleStockes findArticleStockesByReference(String reference);

	public List<String> findReferenceByCodeTegc(String codeTegc);

	public List<EuArticleStockes> findDesignationByCodeTegc(String codeTegc);
}
