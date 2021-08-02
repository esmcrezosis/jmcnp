package com.esmc.mcnp.infrastructure.services.bc;

import java.util.List;
import java.util.Optional;

import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuBonService extends BaseService<EuBon, Integer> {
	boolean emettreBa(String type, String codeMembre, double montant);
	
	Optional<EuBon> emettreBon(String type, String codeMembre, double montant);
	
	Optional<EuBon> emettreBon(String type, String codeMembre, String codeMembreAch, double montant);

	List<EuBon> findBonNonExprimer(String codeMembre, String typeBon);

	EuBon findByBonCode(String codeBon, int exprimer);
	
	Long findByMaxIdInserted();
	
	Double findSumAllBonConso();
}
