/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.others.EuOffreDemandeCloture;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuOffreDemandeClotureRepository extends BaseRepository<EuOffreDemandeCloture, Long> {
	@Query("select max(odc.idCloture) from EuOffreDemandeCloture odc")
	public Long getLastClotureInserted();

	public EuOffreDemandeCloture findByNumOffreDemande(String numeroOffreDemande);

	@Query("select odc from EuOffreDemandeCloture odc where odc.codeCompteOffre like :codeCompteOf and odc.codeCompteDemande like :codeCompteDem and cloture = 2")
	public EuOffreDemandeCloture findByCompteOffreAndCompteDemande(@Param("codeCompteOf") String compteOffre,
			@Param("codeCompteDem") String compteDemande);
}
