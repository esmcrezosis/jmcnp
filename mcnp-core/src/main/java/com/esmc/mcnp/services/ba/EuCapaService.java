package com.esmc.mcnp.services.ba;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCapaService extends BaseService<EuCapa, String> {
	List<EuCapa> findByCodeMembre(String codeMembre);

	List<EuCapa> fetchByCodeMembre(String codeMembre);

	List<EuCapa> findByMembreAndOrigine(String codeMembre, String origine);

	List<EuCapa> findByMembreAndOrigine(String codeMembre, List<String> origines);

	List<EuCapa> findbyMembreAndProduitAndOrigine(String codeMembre, String produit, String origine);

	List<EuCapa> findByType(String typeCapa);

	List<EuCapa> findByType(String codeMembre, String typeCapa);

	List<EuCapa> findByCompteAndType(String codeCompte, String typeCapa);

	List<EuCapa> findByMembreAndType(String codeMembre, String typeCapa);

	List<EuCapa> findByMembreAndTypeAndOrigine(String codeMembre, String typeCapa, String origine);

	Boolean verifierCompte(String codeMembre);

	Boolean verifierCompte(String codeMembre, Double soldeCompte);

	Boolean verifierSolde(String codeMembre, Double montant);

	boolean updateCapa(List<EuCapa> capas, Double montant);

	List<String> findProduitByMembreAndOrigine(String codeMembre, String origine);

	double findSumCapaByMembreAndOrigine(String codeMembre, String origine);

	double findSumCapaByMembreAndOrigine(String codeMembre, List<String> origines);

	Page<EuCapa> findByCodeMembre(String codeMembre, Pageable pageable);

	Page<EuCapa> findByMembreAndOrigine(String codeMembre, String origineCapa, Pageable pageable);

	Page<EuCapa> findByCompte(String codeCompte, Pageable pageable);

	Page<EuCapa> findByCompteAndOrigine(String codeCompte, String origine, Pageable pageable);
}
