/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuGcpPrelever;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuGcpPreleverRepository extends BaseRepository<EuGcpPrelever, Long> {
	@Query("select max(gp.idPrelevement) From EuGcpPrelever gp")
	public Long getLastInsertId();

	public List<EuGcpPrelever> findByIdTpagcp(Long idtpagcp);

	@Query("select gp from EuGcpPrelever gp join fetch gp.euGcp gc join fetch gc.euCompteCredit cc where gp.soldePrelevement > 0 and gp.idTpagcp = :idtpagcp")
	public List<EuGcpPrelever> findGcpPreleverByIdTpagcp(@Param("idtpagcp") Long idtpagcp);
}
