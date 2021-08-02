/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.obpsd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuEscompte;

/**
 *
 * @author USER
 */
public interface EuEscompteRepository extends BaseRepository<EuEscompte, Long> {

	@Query("select max(c.idEscompte) from EuEscompte c")
	public Long getLastInsertedId();

	@Query("select e from EuEscompte e join fetch e.euCompte where e.solde > 0 and e.resteNtf > 0")
	public List<EuEscompte> findAllEscompte();

	@Query("select e from EuEscompte e where e.solde > 0 and e.codeMembreBenef like :membre")
	public List<EuEscompte> findEscompteByMembre(@Param("membre") String codeMembre);

	@Query("select sum(e.solde) from EuEscompte e where e.solde > 0 and e.codeMembreBenef like :membreBenef")
	public Double getSommeEscompteByCompte(@Param("membreBenef") String codeMembre);

	@Query("select e from EuEscompte e where e.montEchu > 0 and e.codeMembreBenef like :membreBenef")
	public List<EuEscompte> findEscompteEchuByCompte(@Param("membreBenef") String codeMembre);

	@Query("select sum(e.montEchu) from EuEscompte e where e.montEchu > 0 and e.codeMembreBenef like :membreBenef")
	public Double getSommeEscompteEchuByCompte(@Param("membreBenef") String codeMembre);
}
