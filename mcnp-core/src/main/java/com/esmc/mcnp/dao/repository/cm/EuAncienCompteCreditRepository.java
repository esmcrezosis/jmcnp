package com.esmc.mcnp.dao.repository.cm;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuAncienCompteCredit;

public interface EuAncienCompteCreditRepository extends BaseRepository<EuAncienCompteCredit, Long> {

	List<EuAncienCompteCredit> findByCodeMembre(String codeMembre);

	List<EuAncienCompteCredit> findByCodeMembreAndRembourser(String codeMembre, boolean rembourser);
	
	List<EuAncienCompteCredit> findByCodeCompte(String codeCompte);

	List<EuAncienCompteCredit> findByCodeCompteAndRembourser(String codeCompte, boolean rembourser);
	
	List<EuAncienCompteCredit> findByCodeMembreAndCodeProduit(String codeMembre, String codeProduit);

	@Query("select sum(cc.montantPlace) From EuAncienCompteCredit cc where cc.codeMembre like :membre")
	Optional<Double> getCompteSumCapaByCodeMembre(@Param("membre") String codeMembre);

	@Query("select sum(cc.montantCredit) From EuAncienCompteCredit cc where cc.codeMembre like :membre")
	Optional<Double> getCompteSumCreditByCodeMembre(@Param("membre") String codeMembre);

	@Query("select sum(cc.montantPlace) From EuAncienCompteCredit cc where cc.codeMembre like :membre and cc.codeProduit like :produit")
	Optional<Double> getCompteSumCapaByCodeMembre(@Param("membre") String codeMembre, @Param("produit") String codeProduit);

	@Query("select sum(cc.montantCredit) From EuAncienCompteCredit cc where cc.codeMembre like :membre and cc.codeProduit like :produit")
	Optional<Double> getCompteSumCreditByCodeMembre(@Param("membre") String codeMembre, @Param("produit") String codeProduit);

	@Query("select sum(cc.montantPlace) From EuAncienCompteCredit cc where cc.codeMembre like :membre and cc.rembourser = :remb")
	Optional<Double> getSumCapaByMembre(@Param("membre") String codeMembre, @Param("remb") boolean rembourser);

	@Query("select sum(cc.montantCredit) From EuAncienCompteCredit cc where cc.codeMembre like :membre and cc.rembourser = :remb")
	Optional<Double> getSumCreditByMembre(@Param("membre") String codeMembre, @Param("remb") boolean rembourser);

	@Query("select sum(cc.montantPlace) From EuAncienCompteCredit cc where cc.codeMembre like :membre and cc.codeProduit like :produit and cc.rembourser = :remb")
	Optional<Double> getSumCapaByMembre(@Param("membre") String codeMembre, @Param("produit") String codeProduit, @Param("remb") boolean rembourser);

	@Query("select sum(cc.montantCredit) From EuAncienCompteCredit cc where cc.codeMembre like :membre and cc.codeProduit like :produit and cc.rembourser = :remb")
	Optional<Double> getSumCreditByMembre(@Param("membre") String codeMembre, @Param("produit") String codeProduit, @Param("remb") boolean rembourser);
}
