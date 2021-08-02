package com.esmc.mcnp.infrastructure.services.franchise;

import java.util.List;

import com.esmc.mcnp.domain.entity.franchise.EuAchatFranchises;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuAchatFranchisesService extends BaseService<EuAchatFranchises, Long> {

	EuAchatFranchises findByAssociation(Long idAssociation);

	List<EuAchatFranchises> findByAssociation(String nomAssociation);
	
	List<EuAchatFranchises> findByOdd(Long idOdd);

	List<EuAchatFranchises> findByOdd(String titreOdd);

	List<EuAchatFranchises> findByCanton(Long idCanton);

	List<EuAchatFranchises> findByCanton(String nomCanton);

	List<EuAchatFranchises> findByPrefecture(Long idPrefecture);

	List<EuAchatFranchises> findByPrefecture(String nomPrefecture);

	List<EuAchatFranchises> findByRegion(Long idRegion);

	List<EuAchatFranchises> findByRegion(String nomRegion);

	List<EuAchatFranchises> findByPays(Long idPays);

	List<EuAchatFranchises> findByPays(String libPays);
}
