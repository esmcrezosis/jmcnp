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
import com.esmc.mcnp.domain.entity.obpsd.EuDetailGcpPbf;

/**
 *
 * @author USER
 */
public interface EuDetailGcpPbfRepository extends BaseRepository<EuDetailGcpPbf, Long> {

	@Query("select max(dt.idGcpPbf) from EuDetailGcpPbf dt")
	public Long getLastInsertedId();

	public List<EuDetailGcpPbf> findByEuGcpPbf_CodeGcpPbf(String codeGcpPbf);

	@Query("select d from EuDetailGcpPbf d join d.euGcpPbf g where d.soldeGcpPbf > 0 and g.codeGcpPbf like :gcpPbf")
	public List<EuDetailGcpPbf> findDetailByGcpPbfB(@Param("gcpPbf") String codeGcpPbf);

	@Query("select d from EuDetailGcpPbf d where d.soldeGcpPbf > 0 and d.idEscompte = :id")
	List<EuDetailGcpPbf> findDetailByEscompte(@Param("id") long id_escompte);

	@Query("select d from EuDetailGcpPbf d where d.soldeGcpPbf > 0 and d.idEchange = :id")
	List<EuDetailGcpPbf> findDetailByEchange(@Param("id") long id_echange);

}
