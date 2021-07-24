/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.cm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuCompteCreditTsRepository extends BaseRepository<EuCompteCreditTs, Long> {

	@Query("select ccts from EuCompteCreditTs ccts join fetch ccts.euCompteCredit cc join ccts.euCompte c where c.codeCompte= :compte")
	public List<EuCompteCreditTs> findByEuCompte_CodeCompte(@Param("compte") String codeCompte);

	@Query("select ccts from EuCompteCreditTs ccts join fetch ccts.euCompteCredit cc join ccts.euCompte c join cc.euProduit p where c.codeCompte= :compte and ccts.montant > 0 and p.codeProduit= :produit and cc.codeTypeCredit= :credit")
	public List<EuCompteCreditTs> findByProduitAndCredit(@Param("compte") String codeCompte,
			@Param("produit") String codeProduit, @Param("credit") String codeTypeCredit);

	@Query("select ccts from EuCompteCreditTs ccts join fetch ccts.euCompteCredit cc join ccts.euCompte c join cc.euProduit p where c.codeCompte= :compte and ccts.montant > 0 and p.codeProduit= :produit ")
	public List<EuCompteCreditTs> findByProduit(@Param("compte") String codeCompte,
			@Param("produit") String codeProduit);

	@Query("select sum(ccts.montant) from EuCompteCreditTs ccts join ccts.euCompteCredit cc where ccts.euCompte.codeCompte= :compte and ccts.montant > 0 and cc.euProduit.codeProduit= :produit and cc.codeTypeCredit= :credit ")
	public Double getSumCreditByEuCompte(@Param("compte") String codeCompte, @Param("produit") String codeProduit,
			@Param("credit") String codeTypeCredit);

	@Query("select ccts from EuCompteCreditTs ccts join fetch ccts.euCompteCredit cc where ccts.montant > 0 and cc.idCredit = :id")
	public List<EuCompteCreditTs> findByIdCredit(@Param("id") Long idCredit);

	@Query("select ccts from EuCompteCreditTs ccts join fetch ccts.euCompteCredit cc where ccts.idCreditTs = :idts")
	public EuCompteCreditTs findByKey(@Param("idts") Long id);

	@Query("select ccts from EuCompteCreditTs ccts join ccts.euCompteCredit cc join ccts.euCompte c where c.codeCompte like :compte and cc.codeTypeCredit like :credit and ccts.montant > 0")
	public List<EuCompteCreditTs> findByCompteAndCredit(@Param("compte") String codeCompte,
			@Param("credit") String typeCredit);

	@Query("select ccts from EuCompteCreditTs ccts join ccts.euCompteCredit cc join ccts.euCompte c join cc.euProduit p where ccts.montant > 0 and c.codeCompte like :compte and p.codeProduit like :produit and cc.codeTypeCredit like :credit")
	public List<EuCompteCreditTs> findByCompteAndProduitAndCredit(@Param("compte") String codeCompte,
			@Param("produit") String produit, @Param("credit") String typeCredit);

	public List<EuCompteCreditTs> findByEuBon_BonNumero(String numero);

	@Query("select ccts from EuCompteCreditTs ccts join fetch ccts.euCompteCredit cc join ccts.euBon b where b.bonNumero like :numero")
	public List<EuCompteCreditTs> findByBonNumero(@Param("numero") String numero);

	public List<EuCompteCreditTs> findByEuBon_BonId(@Param("bonId") Integer bonId);
}
