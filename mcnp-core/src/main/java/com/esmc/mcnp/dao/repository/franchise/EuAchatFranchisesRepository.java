package com.esmc.mcnp.dao.repository.franchise;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.franchise.EuAchatFranchises;

public interface EuAchatFranchisesRepository extends BaseRepository<EuAchatFranchises, Long> {

	@EntityGraph(attributePaths = { "association", "odd" })
	EuAchatFranchises findByAssociation_AssociationId(Long idAssociation);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByAssociation_AssociationNomLike(String nomAssociation);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByOdd_Titre(String titreOdd);
	
	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByOdd_IdOdd(Long idOdd);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByCanton_IdCanton(Long idCanton);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByCanton_NomCanton(String nomCanton);
	
	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByPrefecture_IdPrefecture(Long idPrefecture);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByPrefecture_NomPrefecture(String nomPrefecture);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByRegion_IdRegion(Long idRegion);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByRegion_NomRegion(String nomRegion);

	@EntityGraph(attributePaths = { "association", "odd" })
	List<EuAchatFranchises> findByPays_IdPays(Long idPays);

	List<EuAchatFranchises> findByPays_LibellePays(String libPays);
}
