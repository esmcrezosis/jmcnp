package com.esmc.mcnp.dao.repository.pc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuDetailPaiement;

public interface EuDetailPaiementRepository extends BaseRepository<EuDetailPaiement, Integer> {

	public EuDetailPaiement findByTableAndIdTable(String table, Integer id);

	@Query("select d from EuDetailPaiement d where d.euPaiement.euDemandePaiement.numeroDemandePaiement like :numero")
	public List<EuDetailPaiement> findContratByNumeroDemandePaiement(@Param("numero") String numero);

	@Query("select d from EuDetailPaiement d where d.euPaiement.euDemandePaiement.idDemandePaiement like :id")
	public List<EuDetailPaiement> findDetailPaiementByIdDemandePaiement(@Param("id") Integer numero);
}
