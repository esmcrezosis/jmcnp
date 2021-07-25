package com.esmc.mcnp.services.cm;

import java.util.List;

import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCompteCreditTsService extends BaseService<EuCompteCreditTs, Long> {

	public boolean verifierCompte(String codeCompte);

	public boolean verifierSolde(String codeCompte, Double montant);

	public boolean verifierSolde(String codeCompte, String produit, Double montant);

	public boolean verfierSolde(String codeCompte, String produit, String typeCredit, Double montant);
	
	public boolean verifierSoldeCredit(String codeCompte, String typeCredit, Double montant);

	public List<EuCompteCreditTs> findByCompte(String codeCompte);

	public List<EuCompteCreditTs> findByCompteAndProduit(String codeCompte, String produit);
	
	public List<EuCompteCreditTs> findByCompteAndTypeCredit(String codeCompte, String typeCredit);

	public List<EuCompteCreditTs> findByCompteAndProduitAndTypeCredit(String codeCompte, String produit,
			String typeCredit);
	
	 public List<EuCompteCreditTs> findByEuBon_BonNumero(String numero);
}
