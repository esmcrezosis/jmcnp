/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.ksu;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.ksu.EuCarteRepository;
import com.esmc.mcnp.domain.entity.ksu.EuCarte;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

/**
 *
 * @author HP
 */
@Service("euCarteService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCarteServiceImplImpl extends CrudServiceImpl<EuCarte, Long> implements EuCarteService {

	/**
	 * 
	 */
	private @Autowired final EuCarteRepository carteRepository;

	protected EuCarteServiceImplImpl(EuCarteRepository carteRepository) {
		super(carteRepository);
		this.carteRepository = carteRepository;
	}

	protected BaseRepository<EuCarte, Long> getRepository() {
		return carteRepository;
	}

	@Override
	public List<EuCarte> findByDateDemande(LocalDate dateDemande) {
		return carteRepository.findByDateDemande(dateDemande);
	}

	@Override
	public Page<EuCarte> findByDateDemande(LocalDate dateDemande, Pageable pageable) {

		return carteRepository.findByDateDemande(dateDemande, pageable);
	}

	@Override
	public List<EuCarte> findByDate(LocalDate date) {
		return carteRepository.findByDate(date);
	}

	@Override
	public List<EuCarte> findByDateDemandeBetween(LocalDate datedeb, LocalDate datefin) {
		return carteRepository.findByDateDemandeBetween(datedeb, datefin);
	}

	@Override
	public Page<EuCarte> findByDateDemandeBetween(LocalDate datedeb, LocalDate datefin, Pageable pageable) {
		return carteRepository.findByDateDemandeBetween(datedeb, datefin, pageable);
	}

	@Override
	public List<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer) {
		return carteRepository.findByDateDemandeAndImprimer(dateDemande, imprimer);
	}

	@Override
	public List<EuCarte> findByDateDemandeAndLivrer(LocalDate dateDemande, boolean livrer) {
		return carteRepository.findByDateAndLivrer(dateDemande, livrer);
	}

	@Override
	public List<EuCarte> findByDateAndImprimer(LocalDate date, boolean imprimer) {
		return carteRepository.findByDateAndImprimer(date, imprimer);
	}

	@Override
	public List<EuCarte> findByDateAndLivrer(LocalDate date, boolean livrer) {
		return carteRepository.findByDateAndLivrer(date, livrer);
	}

	@Override
	public List<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, LocalDate datedeb, LocalDate datefin) {
		return carteRepository.findByImprimerAndDateDemandeBetween(imprimer, datedeb, datefin);
	}

	@Override
	public List<EuCarte> findByLivrerAndDateDemandeBetween(boolean livrer, LocalDate datedeb, LocalDate datefin) {
		return carteRepository.findByLivrerAndDateDemandeBetween(livrer, datedeb, datefin);
	}

	@Override
	public List<EuCarte> findByImprimer(boolean imprimer) {
		return carteRepository.findByImprimer(imprimer);
	}

	@Override
	public List<EuCarte> findByLivrer(boolean livrer) {
		return carteRepository.findByLivrer(livrer);
	}

	@Override
	public List<EuCarte> findByCodeMembre(String codeMembre) {
		return carteRepository.findByEuMembre_CodeMembre(codeMembre);
	}

	@Override
	public Page<EuCarte> findByCodeMembre(String codeMembre, Pageable pageable) {
		return carteRepository.findByEuMembre_CodeMembre(codeMembre, pageable);
	}

	@Override
	public EuCarte findByMembre(String codeMembre) {
		return carteRepository.findByCodeMembre(codeMembre);
	}

	@Override
	public List<EuCarte> findByCodeMembreMorale(String codeMembre) {
		return carteRepository.findByEuMembreMorale_CodeMembreMorale(codeMembre);
	}

	@Override
	public Page<EuCarte> findByCodeMembreMorale(String codeMembre, Pageable pageable) {
		return carteRepository.findByEuMembreMorale_CodeMembreMorale(codeMembre, pageable);
	}

	@Override
	public EuCarte findByMembreMorale(String codeMembre) {
		return carteRepository.findByCodeMembreMorale(codeMembre);
	}

	@Override
	public List<EuCarte> findByImprimer(boolean imprimer, Long idUser) {
		return carteRepository.findByImprimerAndIdUtilisateur(imprimer, idUser);
	}

	@Override
	public List<EuCarte> findByLivrer(boolean livrer, Long idUser) {
		return carteRepository.findByLivrerAndIdUtilisateur(livrer, idUser);
	}

	@Override
	public List<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer, Long idUser) {
		return carteRepository.findByDateDemandeAndImprimerAndIdUtilisateur(dateDemande, imprimer, idUser);
	}

	@Override
	public List<EuCarte> findByDateDemandeAndLivrer(LocalDate dateDemande, boolean livrer, Long idUser) {
		return carteRepository.findByDateDemandeAndLivrerAndIdUtilisateur(dateDemande, livrer, idUser);
	}

	@Override
	public List<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, Long idUser, LocalDate datedeb,
			LocalDate datefin) {
		return carteRepository.findByImprimerAndIdUtilisateurAndDateDemandeBetween(imprimer, idUser, datedeb, datefin);
	}

	@Override
	public List<EuCarte> findByLivrerAndDateDemandeBetween(boolean livrer, Long idUser, LocalDate datedeb,
			LocalDate datefin) {
		return carteRepository.findByLivrerAndIdUtilisateurAndDateDemandeBetween(livrer, idUser, datedeb, datefin);
	}

	@Override
	public List<EuCarte> findByDate(LocalDate date, Long idUser) {
		return carteRepository.findByDate(date, idUser);
	}

	@Override
	public List<EuCarte> findByDateAndImprimer(LocalDate date, boolean imprimer, Long idUser) {
		return carteRepository.findByDateAndImprimer(date, imprimer, idUser);
	}

	@Override
	public List<EuCarte> findByDateAndLivrer(LocalDate date, boolean livrer, Long idUser) {
		return carteRepository.findByDateAndLivrer(date, livrer, idUser);
	}

	@Override
	public EuCarte findNonImprimerByMembreMorale(String codeMembre, boolean imprimer) {
		return carteRepository.findNonImprimerByMembreMorale(codeMembre, imprimer);
	}

	@Override
	public EuCarte findNonImprimerByMembre(String codeMembre, boolean imprimer) {
		return carteRepository.findNonImprimerByMembre(codeMembre, imprimer);
	}

	@Override
	public EuCarte findNonLivrerByMembreMorale(String codeMembre, boolean livrer) {
		return carteRepository.findNonLivrerByMembreMorale(codeMembre, livrer);
	}

	@Override
	public EuCarte findNonLivrerByMembre(String codeMembre, boolean livrer) {
		return carteRepository.findNonLivrerByMembre(codeMembre, livrer);
	}

	@Override
	public List<EuCarte> findKsuByMembre(String codeMembre) {
		if (codeMembre.endsWith("M")) {
			return findByCodeMembreMorale(codeMembre);
		} else {
			return findByCodeMembre(codeMembre);
		}
	}

	@Override
	public Page<EuCarte> findKsuByMembre(String codeMembre, Pageable pageable) {
		if (codeMembre.endsWith("M")) {
			return findByCodeMembreMorale(codeMembre, pageable);
		} else {
			return findByCodeMembre(codeMembre, pageable);
		}
	}

	@Override
	public Page<EuCarte> findByImprimer(boolean imprimer, Long idUser, Pageable page) {
		return carteRepository.findByImprimerAndIdUtilisateur(imprimer, idUser, page);
	}

	@Override
	public Page<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer, Long idUser,
			Pageable page) {
		return carteRepository.findByDateDemandeAndImprimerAndIdUtilisateur(dateDemande, imprimer, idUser, page);
	}

	@Override
	public Page<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, Long idUser, LocalDate datedeb,
			LocalDate datefin, Pageable page) {
		return carteRepository.findByImprimerAndIdUtilisateurAndDateDemandeBetween(imprimer, idUser, datedeb, datefin,
				page);
	}

	@Override
	public Page<EuCarte> findByImprimerAndDateDemandeBetween(boolean imprimer, LocalDate datedeb, LocalDate datefin,
			Pageable page) {
		return carteRepository.findByImprimerAndAndDateDemandeBetween(imprimer, datedeb, datefin, page);
	}

	@Override
	public Page<EuCarte> findByUtilisateurAndDateIn(Long id, LocalDate datedeb, LocalDate datefin, Pageable pageable) {
		return carteRepository.findByIdUtilisateurAndDateDemandeBetween(id, datedeb, datefin, pageable);
	}

	@Override
	public Page<EuCarte> findByDateInf(LocalDate dateFin, Pageable pageable) {
		return carteRepository.findByDateDemandeLessThan(dateFin, pageable);
	}

	@Override
	public Page<EuCarte> findByDateSup(LocalDate dateFin, Pageable pageable) {
		return carteRepository.findByDateDemandeGreaterThan(dateFin, pageable);
	}

	@Override
	public Page<EuCarte> findByUtilisateurAndDateInf(Long id, LocalDate dateFin, Pageable pageable) {
		return carteRepository.findByIdUtilisateurAndDateDemandeLessThan(id, dateFin, pageable);
	}

	@Override
	public Page<EuCarte> findByUtilisateurAndDateSup(Long id, LocalDate dateFin, Pageable pageable) {
		return carteRepository.findByIdUtilisateurAndDateDemandeGreaterThan(id, dateFin, pageable);
	}

	@Override
	public Page<EuCarte> findByUser(Long idUser, Pageable pageable) {
		return carteRepository.findByIdUtilisateur(idUser, pageable);
	}

	@Override
	public Page<EuCarte> findByImprimer(boolean imprimer, Pageable page) {
		return carteRepository.findByImprimer(imprimer, page);
	}

	@Override
	public Page<EuCarte> findByDateDemandeAndImprimer(LocalDate dateDemande, boolean imprimer, Pageable page) {
		return carteRepository.findByDateAndImprimer(dateDemande, imprimer, page);
	}

}
