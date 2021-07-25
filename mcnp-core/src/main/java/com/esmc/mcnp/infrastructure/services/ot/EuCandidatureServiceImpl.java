package com.esmc.mcnp.services.ot;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.ot.EuCandidature;
import com.esmc.mcnp.repositories.ot.EuCandidatureRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
@Transactional(readOnly = true)
public class EuCandidatureServiceImpl extends CrudServiceImpl<EuCandidature, Integer> implements EuCandidatureService {

	private final EuCandidatureRepository candidatureRepository;
	
	protected EuCandidatureServiceImpl(EuCandidatureRepository candidatureRepository) {
		super(candidatureRepository);
		this.candidatureRepository = candidatureRepository;
	}

	@Override
	public List<EuCandidature> findByDateExpiration(LocalDate dateExp) {
		return candidatureRepository.findByDateExpiration(dateExp);
	}

	@Override
	public Page<EuCandidature> findByDateExpiration(LocalDate dateExp, Pageable pageable) {
		return candidatureRepository.findByDateExpiration(dateExp, pageable);
	}

	@Override
	public List<EuCandidature> findByDate(LocalDate date) {
		return candidatureRepository.findByDate(date);
	}

	@Override
	public Page<EuCandidature> findByDate(LocalDate date, Pageable pageable) {
		return candidatureRepository.findByDate(date, pageable);
	}

	@Override
	public List<EuCandidature> findByPosteId(Integer idPoste) {
		return candidatureRepository.findByPosteId(idPoste);
	}

	@Override
	public List<EuCandidature> findByCodePoste(String codePoste) {
		return candidatureRepository.findByCodePoste(codePoste);
	}

	@Override
	public List<EuCandidature> findByLibellePoste(String libelle) {
		return candidatureRepository.findByLibellePoste(libelle);
	}

	@Override
	public Page<EuCandidature> findByPosteId(Integer idPoste, Pageable pageable) {
		return candidatureRepository.findByPosteId(idPoste, pageable);
	}

	@Override
	public Page<EuCandidature> findByCodePoste(String codePoste, Pageable pageable) {
		return candidatureRepository.findByCodePoste(codePoste, pageable);
	}

	@Override
	public Page<EuCandidature> findByLibellePoste(String libelle, Pageable pageable) {
		return candidatureRepository.findByLibellePoste(libelle, pageable);
	}

}
