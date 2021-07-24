package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuBonGcpPrelever;
import com.esmc.mcnp.model.obps.EuBonGcpPreleverPK;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuBonGcpPreleverRepository extends BaseRepository<EuBonGcpPrelever, EuBonGcpPreleverPK> {

	@Query("select bg From EuBonGcpPrelever  bg join fetch bg.euGcpPrelever g join bg.euBon b where b.bonId = :id")
	public List<EuBonGcpPrelever> findByBonId(@Param("id") Integer bonId);

	@Query("select bg From EuBonGcpPrelever  bg join fetch bg.euGcpPrelever g join bg.euBon b where b.bonNumero like :numero")
	public List<EuBonGcpPrelever> findByBonNumero(@Param("numero") String numeroBon);

	@Query("select count(distinct g.idTpagcp) From EuBonGcpPrelever g join g.euBon b where b.bonNumero like :numero")
	Integer countTpagcpByBonNumero(@Param("numero") String numero);

	@Query("select distinct g.idTpagcp From EuBonGcpPrelever g join g.euBon b where b.bonNumero like :numero")
	List<Long> findIdTpagcpByBonNumero(@Param("numero") String numero);

	@Query("select bg From EuBonGcpPrelever bg join fetch bg.euGcpPrelever gp join fetch gp.euGcp where bg.idTpagcp = :idtpagcp")
	public List<EuBonGcpPrelever> findByIdTpagcp(@Param("idtpagcp") Long idTpagcp);
}
