package com.esmc.mcnp.services.obpsd;

import java.util.Date;
import java.util.Optional;

import com.esmc.mcnp.model.obpsd.EuRelevebancairedetail;
import com.esmc.mcnp.services.base.BaseService;

public interface EuRelevebancairedetailService extends BaseService<EuRelevebancairedetail, Integer> {
	boolean isReleveBancaireDetailExist(String numero);
	
	Optional<EuRelevebancairedetail> findByNumero(String numero);
	
	Optional<EuRelevebancairedetail> findByNumeroAndPublier(String numero, int publier);

	public String insertReleveBancairedetail(String codeBanque, Date date, String numero, String libelle, double montant,
			Long idUtilisateur);
}
