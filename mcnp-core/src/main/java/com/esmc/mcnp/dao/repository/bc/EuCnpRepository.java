/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.bc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuCnp;

/**
 *
 * @author USER
 */
public interface EuCnpRepository extends BaseRepository<EuCnp, Long> {

	@Query("select max(c.idCnp) from EuCnp c")
	public Long getLastCnpInsertedId();

	@Query("select c from EuCnp c join c.euCompteCredit cc where c.soldeCnp > 0 and cc.idCredit = :id and c.sourceCredit like :source")
	public EuCnp findBySourceCredit(@Param("id") Long id, @Param("source") String source);
	
	@Query("select c from EuCnp c join c.euCompteCredit cc where cc.idCredit = :id and c.sourceCredit like :source")
	public EuCnp findCnpBySourceCredit(@Param("id") Long id, @Param("source") String source);

	@Query("select c from EuCnp c join c.euCompteCredit cc where c.soldeCnp > 0 and cc.idCredit = :id")
	public List<EuCnp> findByIdCredit(@Param("id") Long idCredit);
	
	public EuCnp findByEuGcp_IdGcp(Long id);
}
