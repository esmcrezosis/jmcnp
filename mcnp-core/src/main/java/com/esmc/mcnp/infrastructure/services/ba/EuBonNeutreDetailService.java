package com.esmc.mcnp.infrastructure.services.ba;

import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreDetail;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface EuBonNeutreDetailService extends CrudService<EuBonNeutreDetail, Integer> {

	Page<EuBonNeutreDetail> getAll(Pageable pageable);

	Page<EuBonNeutreDetail> findByCodeMembre(String codeMembre, Pageable pageable);

	Page<EuBonNeutreDetail> findByNom(String nom, Pageable pageable);

	Page<EuBonNeutreDetail> findByNomAndPrenom(String nomMembre, String prenom, Pageable pageable);

	Page<EuBonNeutreDetail> findByRaisonSociale(String raison, Pageable pageable);

	Page<EuBonNeutreDetail> findByType(String type, Pageable pageable);

	Page<EuBonNeutreDetail> findByBonNeutreCode(String code, Pageable pageable);

	Page<EuBonNeutreDetail> findByBonNeutreId(Integer id, Pageable pageable);

	Page<EuBonNeutreDetail> findByDate(Date deb, Date fin, Pageable pageable);

	Page<EuBonNeutreDetail> findByDateSup(Date deb, Pageable pageable);

	Page<EuBonNeutreDetail> findByDateInf(Date deb, Pageable pageable);

	public boolean verifierSoldeByCode(String code);

	public boolean verifierSoldeByMembre(String codeMembre);

	public Boolean verifierSoldeByCode(String code, Double montant);

	public Boolean verifierSoldebyMembre(String codeMembre, Double montant);

	public Boolean updateBon(List<EuBonNeutreDetail> bons, Double montant);

	public List<EuBonNeutreDetail> findByMembre(String codeMembre);
	
	public double getSoldeByMembre(String codeMembre);
	
	public double getSoldeByCode(String code);
	
	public double getSoldeByBonNeutreId(Integer id);

	public EuBonNeutreDetail findByCode(String code);
	
	public int getLastInsertedId();
	
	Double getSumByCodeMembreAndDate(String codeMembre, Date date);
}
