package com.esmc.mcnp.dao.repository.obpsd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.ba.EuDetailSmsmoney;

public interface EuDetailSmsmoneyRepository extends BaseRepository<EuDetailSmsmoney, Long> {

	public List<EuDetailSmsmoney> findByEuMembreMorale_CodeMembreMorale(String codeMembreMorale);

	@Query("select s  from EuDetailSmsmoney s where s.soldeSms > 0 and s.codeMembre = :membre")
	public List<EuDetailSmsmoney> findByMembre(@Param("membre") String codeMembreMorale);

	@Query("select s  from EuDetailSmsmoney s where s.soldeSms > 0 and s.typeSms = :type")
	public List<EuDetailSmsmoney> findByType(@Param("type") String type);

	@Query("select sum(p.soldeSms) from EuDetailSmsmoney p where p.soldeSms > 0 and p.codeMembre = :membre")
	public Double findSumByMembre(@Param("membre") String codeMembre);

	@Query("select sum(p.soldeSms) from EuDetailSmsmoney p where p.soldeSms > 0 and p.codeMembre = :membre and p.typeSms = :type")
	public Double findSumByTypeNnAndMembre(@Param("membre") String codeMembre, @Param("type") String typeNn);

	@Query("select p from EuDetailSmsmoney p where p.soldeSms > 0 and p.codeMembre = :membre and p.typeSms = :type")
	public List<EuDetailSmsmoney> findByTypeNnAndMembre(@Param("membre") String codeMembre,
			@Param("type") String typeNn);

	@Query("select max(s.idDetailSmsmoney) from EuDetailSmsmoney s")
	public Long getLastDetSmsmooneyInsertedId();

}
