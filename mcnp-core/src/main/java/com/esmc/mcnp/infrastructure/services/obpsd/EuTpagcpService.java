package com.esmc.mcnp.infrastructure.services.obpsd;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuTpagcpService extends BaseService<EuTpagcp, Long> {
	List<EuTpagcp> findByEuCompte_CodeCompte(String codeCompte);

	List<EuTpagcp> findByCodeCompte(String codeCompte);

	List<EuTpagcp> findByMembre(String codeMembre);

	List<EuTpagcp> findByMembre(String codeMembre, Integer ntf);

	Page<EuTpagcp> findAll(Pageable pageable);

	Page<EuTpagcp> findAll(String codeMembre, Pageable pageable);

	Page<EuTpagcp> findByMembreAndDate(String codeMembre, LocalDate dateDeb, LocalDate dateFin, Pageable pageable);

	Page<EuTpagcp> findByMembreDateInf(String codeMembre, LocalDate dateDeb, Pageable pageable);

	Page<EuTpagcp> findByMembreDateSup(String codeMembre, LocalDate dateDeb, Pageable pageable);

	List<EuTpagcp> getAll();

	EuTpagcp getById(Long id);

	Page<EuTpagcp> getAll(Pageable pageable);

	double getSumSoldeTpagcpbyCompte(String codeCompte);

	double getSumEchuTpagcpbyCompte(String codeCompte);

	List<EuTpagcp> findByMembreAndModeReg(String codeMembre, String modeReglement);

	Page<EuTpagcp> findByMembreAndModeReg(String codeMembre, String modeReglement, Pageable pageable);

	List<EuTpagcp> findEchuByMembreAndModeReg(String codeMembre, String modeReglement);

	List<EuTpagcp> findByIds(List<Long> ids);

	Double getSumSoldeByIds(List<Long> ids);

	Long getLastInsertedId();

	List<EuTpagcp> findByDateDebut(LocalDate datedeb);

	List<EuTpagcp> findByDateDebutSup(LocalDate datedeb);

	List<EuTpagcp> findByDateDebInf(LocalDate datedeb);

	Page<EuTpagcp> findByDateDebutSup(LocalDate datedeb, Pageable pageable);

	Page<EuTpagcp> findByDateDebInf(LocalDate datedeb, Pageable pageable);

	List<EuTpagcp> findTpagcpByDateEntre(LocalDate datedeb, LocalDate dateFin);

	List<EuTpagcp> findByDateDebBetween(LocalDate datedeb, LocalDate dateFin);

	Page<EuTpagcp> findByDateDebBetween(LocalDate datedeb, LocalDate dateFin, Pageable pageable);

	List<Long> findAllByCodeMembre(String codeMembre);

	List<EuTpagcp> findByDateFin(LocalDate datedeb, LocalDate dateFin);

	List<EuTpagcp> findByDateFinBetween(LocalDate datedeb, LocalDate dateFin);

	List<EuTpagcp> findByDateFin(LocalDate dateFin);

	List<EuTpagcp> findByDateFinSup(LocalDate dateFin);

	List<EuTpagcp> findByDateFinInf(LocalDate dateFin);

	List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin);

	List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin, Integer ntf);

	List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin, int page);

	List<EuTpagcp> findByDateDebAndFin(LocalDate deb, LocalDate fin, Integer ntf, int page);
}
