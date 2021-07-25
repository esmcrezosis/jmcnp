package com.esmc.mcnp.services.bc;

import java.util.List;
import java.util.Optional;

import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.services.base.BaseService;

public interface EuBonService extends BaseService<EuBon, Integer> {
	boolean emettreBa(String type, String codeMembre, double montant);
	
	Optional<EuBon> emettreBon(String type, String codeMembre, double montant);
	
	Optional<EuBon> emettreBon(String type, String codeMembre, String codeMembreAch, double montant);

	List<EuBon> findBonNonExprimer(String codeMembre, String typeBon);

	EuBon findByBonCode(String codeBon, int exprimer);
	
	Long findByMaxIdInserted();
	
	Double findSumAllBonConso();
}
