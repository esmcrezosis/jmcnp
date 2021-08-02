package com.esmc.mcnp.infrastructure.services.ksu;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.ksu.EuLivraisonKsuRepository;
import com.esmc.mcnp.domain.entity.ksu.EuCarte;
import com.esmc.mcnp.domain.entity.ksu.EuLivraisonKsu;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuLivraisonKsuServiceImplImpl extends CrudServiceImpl<EuLivraisonKsu, Long>
		implements EuLivraisonKsuService {

	private final EuLivraisonKsuRepository livraisonKsuRepository;

	protected EuLivraisonKsuServiceImplImpl(EuLivraisonKsuRepository livraisonKsuRepository) {
		super(livraisonKsuRepository);
		this.livraisonKsuRepository = livraisonKsuRepository;
	}

	@Override
	public List<EuLivraisonKsu> findByUser(Long id) {
		return livraisonKsuRepository.findByUser_IdUtilisateur(id);
	}

	@Override
	public List<EuLivraisonKsu> findByUser(Long id, LocalDate dateDemande) {
		return livraisonKsuRepository.findByUser_IdUtilisateurAndDateDemande(id, dateDemande);
	}

	@Override
	public Page<EuLivraisonKsu> findByUser(Long id, Pageable page) {
		return livraisonKsuRepository.findByUser_IdUtilisateur(id, page);
	}

	@Override
	public Page<EuLivraisonKsu> findByUser(Long id, LocalDate dateDemande, Pageable page) {
		return livraisonKsuRepository.findByUser_IdUtilisateurAndDateDemande(id, dateDemande, page);
	}

	@Override
	public List<EuLivraisonKsu> findByDateDemande(LocalDate dateDemande) {
		return livraisonKsuRepository.findByDateDemande(dateDemande);
	}

	@Override
	public List<EuLivraisonKsu> findByDateDemandeBetween(LocalDate deb, LocalDate fin) {
		return livraisonKsuRepository.findByDateDemandeBetween(deb, fin);
	}

	@Override
	public Page<EuLivraisonKsu> findByDateDemande(LocalDate dateDemande, Pageable page) {
		return livraisonKsuRepository.findByDateDemande(dateDemande, page);
	}

	@Override
	public Page<EuLivraisonKsu> findByDateDemandeBetween(LocalDate deb, LocalDate fin, Pageable page) {
		return livraisonKsuRepository.findByDateDemandeBetween(deb, fin, page);
	}

	@Override
	public EuLivraisonKsu findByCodeSuivi(String code) {
		return livraisonKsuRepository.findByCodeSuivi(code);
	}

	@Override
	public List<EuLivraisonKsu> findByUserAndDateDemande(Long id, LocalDate deb, LocalDate fin) {
		return livraisonKsuRepository.findByUser_IdUtilisateurAndDateDemandeBetween(id, deb, fin);
	}

	@Override
	public Page<EuLivraisonKsu> findByUserAndDateDemande(Long id, LocalDate deb, LocalDate fin, Pageable page) {
		return livraisonKsuRepository.findByUser_IdUtilisateurAndDateDemandeBetween(id, deb, fin, page);
	}

	@Override
	public List<EuCarte> findCarteByLivraison(Long idLivraison) {
		return livraisonKsuRepository.findByLivraison(idLivraison);
	}

}
