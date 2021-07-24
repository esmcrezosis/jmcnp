package com.esmc.mcnp.services.oi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.esmc.mcnp.model.odd.EuMstierListecm;
import com.esmc.mcnp.services.base.CrudService;

public interface EuMstierListecmService extends CrudService<EuMstierListecm, Integer> {
	
	EuMstierListecm findByCodeFicheOdd(String codeFicheOdd);

	List<EuMstierListecm> findByMembreBeneficiaire(String codeMembre);

	Page<EuMstierListecm> findByMembreBeneficiaire(String codeMembre, Pageable pageable);

	List<EuMstierListecm> findDoublonByNom(String nom, String prenom);

	List<EuMstierListecm> findDoublon(String nom, String prenom, LocalDate date, String lieu);

	List<EuMstierListecm> findByOdd(Integer idOdd, Sort sort);

	List<EuMstierListecm> findByAgenceOdd(Integer idAgenceOdd, Sort sort);

	List<EuMstierListecm> findByUser(Long idUser, Sort sort);

	List<EuMstierListecm> findByAgenceOddAndDate(Integer idAgence, LocalDateTime deb, LocalDateTime fin, Sort sort);

	List<EuMstierListecm> findByOddAndDate(Integer idOdd, LocalDateTime deb, LocalDateTime fin, Sort sort);

	Page<EuMstierListecm> findByOdd(Integer odd, Pageable page);

	Page<EuMstierListecm> findByAgenceOdd(Integer agenceOdd, Pageable page);

	Page<EuMstierListecm> findByUser(Long id, Pageable page);

	Page<EuMstierListecm> findByAgenceOddAndDate(Integer idAgence, LocalDateTime deb, LocalDateTime fin, Pageable page);

	Page<EuMstierListecm> findByOddAndDate(Integer idOdd, LocalDateTime deb, LocalDateTime fin, Pageable page);

	Page<EuMstierListecm> findByAgenceAndOddAndDate(Integer agence, Integer odd, LocalDateTime deb, LocalDateTime fin,
			Pageable page);

	Page<EuMstierListecm> findByAgenceAndOdd(Integer agence, Integer odd, Pageable page);

	Page<EuMstierListecm> findByDate(LocalDateTime deb, LocalDateTime fin, Pageable page);
}
