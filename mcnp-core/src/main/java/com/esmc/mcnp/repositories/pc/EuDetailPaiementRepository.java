package com.esmc.mcnp.repositories.pc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.pc.EuDetailPaiement;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuDetailPaiementRepository extends BaseRepository<EuDetailPaiement, Integer> {

	public EuDetailPaiement findByTableAndIdTable(String table, Integer id);

	@Query("select d from EuDetailPaiement d where d.euPaiement.euDemandePaiement.numeroDemandePaiement like :numero")
	public List<EuDetailPaiement> findContratByNumeroDemandePaiement(@Param("numero") String numero);

	@Query("select d from EuDetailPaiement d where d.euPaiement.euDemandePaiement.idDemandePaiement like :id")
	public List<EuDetailPaiement> findDetailPaiementByIdDemandePaiement(@Param("id") Integer numero);
}
