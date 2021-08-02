package com.esmc.mcnp.infrastructure.services.ba;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.domain.entity.ba.EuCapaTs;
import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCapaTsService extends BaseService<EuCapaTs, String> {

	public Boolean updateList(List<EuCapaTs> capatss, double montant);

	public boolean updateListCapaTs(List<EuCapaTs> capas, EuBon bon, EuCompteCredit cc, String typeR, Date date, double capa);

	public Boolean verifierSolde(String codeCompte, Double montant);

	public Boolean verifierCompte(String codeCompte);

	public Boolean verifierCompte(String codeCompte, Double soldeCompte);

	public List<EuCapaTs> findByCompte(String codeCompte);

	public List<EuCapaTs> findByCompteAndOrigine(String codeCompte, String origine);

	public List<EuCapaTs> findByCompteAndOrigineAndProduit(String codeCompte, String origine, String produit);
	
	public List<EuCapaTs> findByEuBon_BonNumero(String numero);
	
	public List<EuCapaTs> findByProduitAndMembre(String produit, String codeMembre);
}
