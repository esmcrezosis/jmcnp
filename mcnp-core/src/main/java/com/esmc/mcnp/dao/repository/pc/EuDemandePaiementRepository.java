package com.esmc.mcnp.dao.repository.pc;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuDemandePaiement;

public interface EuDemandePaiementRepository extends BaseRepository<EuDemandePaiement, Integer> {

	public EuDemandePaiement findByNumeroDemandePaiement(String numero);
	
	@Query("select d from EuDemandePaiement d where d.numeroDemandePaiement like :numero")
	public EuDemandePaiement findByNumeroDemande(@Param("numero") String numero);

	public List<EuDemandePaiement> findByCodeMembreEmployeurAndTypeDemande(String codeMembre, String typeDemande);

	@Query("select d from EuDemandePaiement d where d.codeMembreEmployeur like :membre and d.typeDemande like :type and (d.payer = 0 or d.payer is null)")
	public List<EuDemandePaiement> findByCodeMembreAndType(@Param("membre") String codeMembre,
			@Param("type") String typeDemande);
	
	public List<EuDemandePaiement> findByCodeMembreEmployeurAndPayer(String codeMembre, Integer payer);
	
	@Query("select d From EuDemandePaiement d where d.payer = 0 or d.payer is null")
	public List<EuDemandePaiement> findDemandeNonPayer(Pageable page);
	
	@Query("select d From EuDemandePaiement d where d.payer = 0 or d.payer is null")
	public List<EuDemandePaiement> findDemandeNonPayer();
}
