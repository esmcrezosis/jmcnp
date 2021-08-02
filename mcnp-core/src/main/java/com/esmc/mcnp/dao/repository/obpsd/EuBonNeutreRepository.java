package com.esmc.mcnp.dao.repository.obpsd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutre;

import java.util.Date;

public interface EuBonNeutreRepository extends BaseRepository<EuBonNeutre, Integer> {

	@Query("select max(bn.bonNeutreId) from EuBonNeutre bn")
	int getLastInserted();

	EuBonNeutre findByBonNeutreCode(String numeroBon);
	
	EuBonNeutre findByBonNeutreCodeMembre(String codeMembre);

	Page<EuBonNeutre> findByBonNeutreCodeMembre(String codeMembre, Pageable pageable);

	Page<EuBonNeutre> findByBonNeutreNomAndBonNeutrePrenom(String nom, String prenom, Pageable pageable);

	Page<EuBonNeutre> findByBonNeutreNom(String nom, Pageable pageable);

	Page<EuBonNeutre> findByBonNeutreRaison(String raison, Pageable pageable);

	Page<EuBonNeutre> findByBonNeutreDateBetween(Date deb, Date fin, Pageable pageable);

	Page<EuBonNeutre> findByBonNeutreDateGreaterThan(Date deb, Pageable pageable);

	Page<EuBonNeutre> findByBonNeutreDateLessThanEqual(Date deb, Pageable pageable);

	Page<EuBonNeutre> findByBonNeutreType(String typeBn, Pageable pageable);
}
