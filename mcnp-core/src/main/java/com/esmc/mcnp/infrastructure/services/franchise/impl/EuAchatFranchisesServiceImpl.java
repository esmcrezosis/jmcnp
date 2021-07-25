package com.esmc.mcnp.services.franchise.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.franchise.EuAchatFranchises;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.franchise.EuAchatFranchisesRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;
import com.esmc.mcnp.services.franchise.EuAchatFranchisesService;

@Service
@Transactional
public class EuAchatFranchisesServiceImpl extends BaseServiceImpl<EuAchatFranchises, Long>
		implements EuAchatFranchisesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuAchatFranchisesRepository repository;

	@Override
	protected BaseRepository<EuAchatFranchises, Long> getRepository() {
		return repository;
	}

	@Override
	public EuAchatFranchises findByAssociation(Long idAssociation) {
		return repository.findByAssociation_AssociationId(idAssociation);
	}

	@Override
	public List<EuAchatFranchises> findByAssociation(String nomAssociation) {
		return repository.findByAssociation_AssociationNomLike(nomAssociation);
	}

	@Override
	public List<EuAchatFranchises> findByOdd(Long idOdd) {
		return repository.findByOdd_IdOdd(idOdd);
	}

	@Override
	public List<EuAchatFranchises> findByOdd(String titreOdd) {
		return repository.findByOdd_Titre(titreOdd);
	}

	@Override
	public List<EuAchatFranchises> findByCanton(Long idCanton) {
		return repository.findByCanton_IdCanton(idCanton);
	}

	@Override
	public List<EuAchatFranchises> findByCanton(String nomCanton) {
		return repository.findByCanton_NomCanton(nomCanton);
	}

	@Override
	public List<EuAchatFranchises> findByPrefecture(Long idPrefecture) {
		return repository.findByPrefecture_IdPrefecture(idPrefecture);
	}

	@Override
	public List<EuAchatFranchises> findByPrefecture(String nomPrefecture) {
		return repository.findByPrefecture_NomPrefecture(nomPrefecture);
	}

	@Override
	public List<EuAchatFranchises> findByRegion(Long idRegion) {
		return repository.findByRegion_IdRegion(idRegion);
	}

	@Override
	public List<EuAchatFranchises> findByRegion(String nomRegion) {
		return repository.findByRegion_NomRegion(nomRegion);
	}

	@Override
	public List<EuAchatFranchises> findByPays(Long idPays) {
		return repository.findByPays_IdPays(idPays);
	}

	@Override
	public List<EuAchatFranchises> findByPays(String libPays) {
		return repository.findByPays_LibellePays(libPays);
	}

}
