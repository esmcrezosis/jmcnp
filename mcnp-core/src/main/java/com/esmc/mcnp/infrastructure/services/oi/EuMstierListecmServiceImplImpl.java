package com.esmc.mcnp.infrastructure.services.oi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.odd.EuMstierListecmRepository;
import com.esmc.mcnp.domain.entity.odd.EuMstierListecm;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuMstierListecmServiceImplImpl extends CrudServiceImpl<EuMstierListecm, Integer>
		implements EuMstierListecmService {

	private EuMstierListecmRepository mstierListecmRepository;

	protected EuMstierListecmServiceImplImpl(EuMstierListecmRepository mstierListecmRepository) {
		super(mstierListecmRepository);
		this.mstierListecmRepository = mstierListecmRepository;
	}

	@Override
	public List<EuMstierListecm> findDoublon(String nom, String prenom, LocalDate date, String lieu) {
		return mstierListecmRepository.findBy(nom, prenom, date, lieu);
	}

	@Override
	public List<EuMstierListecm> findByOdd(Integer idOdd, Sort sort) {
		if (Objects.nonNull(sort)) {
			return mstierListecmRepository.findByOdd_idOdd(idOdd, sort);
		} else {
			return mstierListecmRepository.findByOdd_idOdd(idOdd, Sort.unsorted());
		}
	}

	@Override
	public List<EuMstierListecm> findByAgenceOdd(Integer idAgenceOdd, Sort sort) {
		if (Objects.nonNull(sort)) {
			return mstierListecmRepository.findByAgenceOdd_IdAgencesOdd(idAgenceOdd, sort);
		} else {
			return mstierListecmRepository.findByAgenceOdd_IdAgencesOdd(idAgenceOdd, Sort.unsorted());
		}
	}

	@Override
	public List<EuMstierListecm> findDoublonByNom(String nom, String prenom) {
		return mstierListecmRepository.findByNomMembreAndPrenomMembre(nom, prenom);
	}

	@Override
	public List<EuMstierListecm> findByUser(Long idUser, Sort sort) {
		if (Objects.nonNull(sort)) {
			return mstierListecmRepository.findByUser_IdUtilisateur(idUser, sort);
		} else {
			return mstierListecmRepository.findByUser_IdUtilisateur(idUser, Sort.unsorted());
		}
	}

	@Override
	public List<EuMstierListecm> findByAgenceOddAndDate(Integer idAgence, LocalDateTime deb, LocalDateTime fin,
			Sort sort) {
		if (Objects.nonNull(sort)) {
			return mstierListecmRepository.findByAgenceOdd_idAgencesOddAndDateListecmBetween(idAgence, deb, fin, sort);
		} else {
			return mstierListecmRepository.findByAgenceOdd_idAgencesOddAndDateListecmBetween(idAgence, deb, fin,
					Sort.unsorted());
		}
	}

	@Override
	public List<EuMstierListecm> findByOddAndDate(Integer idOdd, LocalDateTime deb, LocalDateTime fin, Sort sort) {
		if (Objects.nonNull(sort)) {
			return mstierListecmRepository.findByOdd_IdOddAndDateListecmBetween(idOdd, deb, fin, sort);
		} else {
			return mstierListecmRepository.findByOdd_IdOddAndDateListecmBetween(idOdd, deb, fin, Sort.unsorted());
		}
	}

	@Override
	public Page<EuMstierListecm> findByOdd(Integer odd, Pageable page) {
		return mstierListecmRepository.findByOdd_idOdd(odd, page);
	}

	@Override
	public Page<EuMstierListecm> findByAgenceOdd(Integer agenceOdd, Pageable page) {
		return mstierListecmRepository.findByAgenceOdd_IdAgencesOdd(agenceOdd, page);
	}

	@Override
	public Page<EuMstierListecm> findByUser(Long id, Pageable page) {
		return mstierListecmRepository.findByUser_IdUtilisateur(id, page);
	}

	@Override
	public Page<EuMstierListecm> findByAgenceOddAndDate(Integer idAgence, LocalDateTime deb, LocalDateTime fin,
			Pageable page) {
		return mstierListecmRepository.findByAgenceOdd_idAgencesOddAndDateListecmBetween(idAgence, deb, fin, page);
	}

	@Override
	public Page<EuMstierListecm> findByOddAndDate(Integer idOdd, LocalDateTime deb, LocalDateTime fin, Pageable page) {
		return mstierListecmRepository.findByOdd_IdOddAndDateListecmBetween(idOdd, deb, fin, page);
	}

	@Override
	public Page<EuMstierListecm> findByAgenceAndOddAndDate(Integer agence, Integer odd, LocalDateTime deb,
			LocalDateTime fin, Pageable page) {
		return mstierListecmRepository.findByAgenceAndOddAndDate(agence, odd, deb, fin, page);
	}

	@Override
	public Page<EuMstierListecm> findByAgenceAndOdd(Integer agence, Integer odd, Pageable page) {
		return mstierListecmRepository.findByAgenceAndOdd(agence, odd, page);
	}

	@Override
	public Page<EuMstierListecm> findByDate(LocalDateTime deb, LocalDateTime fin, Pageable page) {
		return mstierListecmRepository.findByDateListecmBetween(deb, fin, page);
	}

	@Override
	public List<EuMstierListecm> findByMembreBeneficiaire(String codeMembre) {
		return mstierListecmRepository.findByCodeMembreBeneficiaire(codeMembre);
	}

	@Override
	public Page<EuMstierListecm> findByMembreBeneficiaire(String codeMembre, Pageable pageable) {
		return mstierListecmRepository.findByCodeMembreBeneficiaire(codeMembre, pageable);
	}

	@Override
	public EuMstierListecm findByCodeFicheOdd(String codeFicheOdd) {
		return mstierListecmRepository.findByCodeFicheOdd(codeFicheOdd);
	}

}
