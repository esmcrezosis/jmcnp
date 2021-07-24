/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.obpsd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obpsd.EuGcpPbf;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuGcpPbfRepository extends BaseRepository<EuGcpPbf, String> {

	@Query("select gp from EuGcpPbf gp join gp.euCompte c where gp.soldeGcp > 0 and c.codeCompte like :compte")
	public List<EuGcpPbf> findGcpPbfByCompte(@Param("compte") String codeCompte);

	@Query("select gp from EuGcpPbf gp join gp.euCompte c where gp.soldeGcp > 0 and c.codeCompte like :compte and gp.typeCapa like :capa")
	public EuGcpPbf findGcpPbfByCompteAndCapa(@Param("compte") String codeCompte, @Param("capa") String codeCapa);

	@Query("select sum(gp.soldeGcp) from EuGcpPbf gp join gp.euCompte c where gp.soldeGcp > 0 and c.codeCompte like :compte")
	public Double getSumGcpPbfByCompte(@Param("compte") String codeCompte);

	@Query("select sum(gp.soldeGcp) from EuGcpPbf gp join gp.euCompte c where gp.soldeGcp > 0 and c.codeCompte like :compte and gp.typeCapa like :type")
	public Double getSumGcpPbfByCompteCapa(@Param("compte") String codeCompte, @Param("type") String typeCapa);
}
