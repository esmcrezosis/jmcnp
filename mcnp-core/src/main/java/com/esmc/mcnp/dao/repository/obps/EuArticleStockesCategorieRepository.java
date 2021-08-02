/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockesCategorie;

/**
 *
 * @author Administrateur
 */
public interface EuArticleStockesCategorieRepository extends BaseRepository<EuArticleStockesCategorie, Long> {

	/*
	 * @Query("select a from EuArticleStockesCategorie a where a.idArticleStockesCategorie in(select distinct e.articleStockesCategorie from euArticleStockes e where e.categorie like :codeTegc"
	 * ) public List<EuArticleStockesCategorie>
	 * findNomCategorieByCodeTegc(@Param("codeTegc") String codeTegc);
	 */
	
	@Query("select max(c.idArticleStockesCategorie) From EuArticleStockesCategorie c")
	Long findLastInsertedRow();

	@Query("select a from EuArticleStockesCategorie a where a.codeMembreMorale like :codeMembre")
	public List<EuArticleStockesCategorie> findCategorieByCodeMembre(@Param("codeMembre") String codeMembreMorale);

	@Query("select a from EuArticleStockesCategorie a where a.codeTegc like :codeTegc")
	public List<EuArticleStockesCategorie> findCategorieByCodeTegc(@Param("codeTegc") String codeTegc);

}
