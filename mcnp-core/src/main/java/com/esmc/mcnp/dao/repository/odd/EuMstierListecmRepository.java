package com.esmc.mcnp.dao.repository.odd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.odd.EuMstierListecm;

public interface EuMstierListecmRepository extends BaseRepository<EuMstierListecm, Integer> {

	EuMstierListecm findByCodeFicheOdd(String codeFicheOdd);

	List<EuMstierListecm> findByNomMembreAndPrenomMembre(String nomMembre, String prenomMembre);

	List<EuMstierListecm> findByCodeMembreBeneficiaire(String codeMembreBeneficiaire);

	Page<EuMstierListecm> findByCodeMembreBeneficiaire(String codeMembreBeneficiaire, Pageable pageable);

	@Query("select m from EuMstierListecm m where m.nomMembre like :nom and m.prenomMembre like :prenom and m.dateNaisMembre = :nais and lieuNaisMembre like :lieu")
	List<EuMstierListecm> findBy(@Param("nom") String nom, @Param("prenom") String prenom,
			@Param("nais") LocalDate dateNais, @Param("lieu") String lieuNais);

	List<EuMstierListecm> findByOdd_idOdd(Integer odd, Sort sort);

	List<EuMstierListecm> findByAgenceOdd_IdAgencesOdd(Integer agenceOdd, Sort sort);

	List<EuMstierListecm> findByUser_IdUtilisateur(Long id, Sort sort);

	List<EuMstierListecm> findByAgenceOdd_idAgencesOddAndDateListecmBetween(Integer idAgence, LocalDateTime deb,
			LocalDateTime fin, Sort sort);

	List<EuMstierListecm> findByOdd_IdOddAndDateListecmBetween(Integer idOdd, LocalDateTime deb, LocalDateTime fin,
			Sort sort);

	Page<EuMstierListecm> findByOdd_idOdd(Integer odd, Pageable page);

	Page<EuMstierListecm> findByAgenceOdd_IdAgencesOdd(Integer agenceOdd, Pageable page);

	Page<EuMstierListecm> findByUser_IdUtilisateur(Long id, Pageable page);

	Page<EuMstierListecm> findByAgenceOdd_idAgencesOddAndDateListecmBetween(Integer idAgence, LocalDateTime deb,
			LocalDateTime fin, Pageable page);

	Page<EuMstierListecm> findByOdd_IdOddAndDateListecmBetween(Integer idOdd, LocalDateTime deb, LocalDateTime fin,
			Pageable page);

	@Query("select m from EuMstierListecm m where m.agenceOdd.idAgencesOdd = :idAgence and m.odd.idOdd = :idOdd and m.dateListecm between :deb and :fin")
	Page<EuMstierListecm> findByAgenceAndOddAndDate(@Param("idAgence") Integer agence, @Param("idOdd") Integer odd,
			@Param("deb") LocalDateTime deb, @Param("fin") LocalDateTime fin, Pageable page);

	@Query("select m from EuMstierListecm m where m.agenceOdd.idAgencesOdd = :idAgence and m.odd.idOdd = :idOdd")
	Page<EuMstierListecm> findByAgenceAndOdd(@Param("idAgence") Integer agence, @Param("idOdd") Integer odd,
			Pageable page);

	Page<EuMstierListecm> findByDateListecmBetween(LocalDateTime deb, LocalDateTime fin, Pageable page);
}
