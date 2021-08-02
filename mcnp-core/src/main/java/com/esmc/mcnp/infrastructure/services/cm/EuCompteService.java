package com.esmc.mcnp.infrastructure.services.cm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface EuCompteService extends CrudService<EuCompte, String> {

	Page<EuCompte> findAll(Pageable pageable);

	List<EuCompte> findCompteByMembre(String codeMembre);

	Page<EuCompte> findCompteByMembre(String codeMembre, Pageable pageable);

	EuCompte findCompteByMembreAndCategorie(String codeMembre, String codeCategorie);

	Page<EuCompte> findCompteByMembreAndCategorie(String codeMembre, String codeCategorie, Pageable pageable);

	Page<EuCompte> findCompteByCategorie(String codeCategorie, Pageable pageable);

	EuCompte findCompteByMembreAndType(String codeMemre, String typeCompte);

	Page<EuCompte> findCompteByType(String typeCompte, Pageable pageable);

	Page<EuCompte> findCompteByMembreAndType(String codeMemre, String typeCompte, Pageable pageable);

	EuCompte findCompteById(String id);

	EuCompte createOrUpdate(String typeCompte, String catCompte, String codeMembre);

	EuCompte createOrUpdate(String typeCompte, String catCompte, String codeMembre, Double montant);

	void updateCompte(String codeCompte, String codeMembre, String libelleOp, double montant);

	void updateCompte(String codeCompte, String codeMembre, List<Long> credits, String libelleOp, double montant);

	void updateCompte(EuCompte compte, String codeMembre, List<Long> credits, String libelleOp, double montant);

	public void updateCompteWithCnp(EuCompte compte, String codeMembre, List<Long> credits, String libelleOp, double montant);
	
	void updateCompte(EuCompte compte, String codeMembreBenef, String libelleOp, double montant);
}
