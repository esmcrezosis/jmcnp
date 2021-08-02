/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.smcipn;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.others.EuProposition;

/**
 *
 * @author USER
 */
public interface EuAppelOffreRepository extends BaseRepository<EuAppelOffre, Long> {

	@Query("select max(ap.idAppelOffre) from EuAppelOffre ap")
	public Long getLastinsertedId();

	@Query("select ap from EuAppelOffre ap join ap.euPropositions prop where ap.numeroOffre like :offre and prop.disponible = 1 and prop.preselection = 1")
	public EuProposition findPropositionByAppelOffre(@Param("offre") String numeroOffre);

	@Query("select ap from EuAppelOffre ap where ap.numeroOffre = :offre")
	public EuAppelOffre findAppelOffresByNumero(@Param("offre") String numeroOffre);

	@Query("select prop from EuAppelOffre ap join ap.euPropositions prop where ap.numeroOffre = :offre and prop.choixProposition =1")
	public EuProposition findPropositionByNumero(@Param("offre") String numeroOffre);
}
