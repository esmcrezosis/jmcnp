/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.obps.Article;
import com.esmc.mcnp.domain.entity.obps.EuArticleStockes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrateur
 */
public interface EuArticleStockesRepository extends BaseRepository<EuArticleStockes, Long> {
	@Query("select max(a.idArticleStocke) From EuArticleStockes a")
	Long findMaxId();

	@Query("select a from EuArticleStockes a join fetch a.euMembreMorale m where m.codeMembreMorale like :codeMembre")
	public List<EuArticleStockes> findByEuMembreMorale_codeMembreMorale(@Param("codeMembre") String codeMembre);

	@Query("select a from EuArticleStockes a where a.codeBarre like :codebarre")
	public EuArticleStockes findByCodeBarre(@Param("codebarre") String codeBarre);

	@Query("select a from EuArticleStockes a join fetch a.euMembreMorale m where a.reference like :reference")
	public EuArticleStockes findByReference(@Param("reference") String reference);

	@Query("select distinct a.reference from EuArticleStockes a where a.articleStockesCategorie =:articleStockesCategorie")
	public List<String> findReferenceByCategorie(@Param("articleStockesCategorie") Integer articleStockesCategorie);

	@Query(value = "select distinct code_membre_morale, reference, designation, prix from eu_article_stockes", nativeQuery = true)
	public List<Article> getAll();

	@Query("select a.reference from EuArticleStockes a where a.categorie =:codeTegc and a.qteStock is not null and a.qteSolde > 0")
	public List<String> findReferenceByCodeTegc(@Param("codeTegc") String codeTegc);

	@Query("select a from EuArticleStockes a where a.categorie =:codeTegc and a.qteStock is not null and a.qteSolde > 0")
	public List<EuArticleStockes> findDesignationByCodeTegc(@Param("codeTegc") String codeTegc);
}
