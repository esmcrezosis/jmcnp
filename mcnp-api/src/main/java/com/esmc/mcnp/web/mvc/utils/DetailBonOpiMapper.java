package com.esmc.mcnp.web.mvc.utils;

import org.springframework.stereotype.Component;

import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.web.mvc.dto.PayerOpi;

@Component
public class DetailBonOpiMapper {

	public DetailBonOpiMapper() {
	}

	public EuDetailBonOpi getDetailBon(PayerOpi paie, EuBon bon) {
		EuDetailBonOpi detBonOpi = new EuDetailBonOpi();
		detBonOpi.setCodeMembreEmetteur(paie.getCodeMembre());
		detBonOpi.setCodeMembrePbf(paie.getCodeMembrePbf());
		detBonOpi.setEuBon(bon);
		detBonOpi.setNbre(paie.getNbre());
		detBonOpi.setModeReg(paie.getModeReg());
		detBonOpi.setTypeEsc(paie.getTypeEsc());
		detBonOpi.setMontant(paie.getMontant());
		return detBonOpi;
	}

	public PayerOpi getDetailOpiPayer(EuDetailBonOpi detBonOpi, String numeroBon) {
		PayerOpi opi = new PayerOpi();
		opi.setCodeMembre(detBonOpi.getCodeMembreEmetteur());
		opi.setCodeMembrePbf(detBonOpi.getCodeMembrePbf());
		opi.setModeReg(detBonOpi.getModeReg());
		opi.setMontant(detBonOpi.getMontant());
		opi.setTypeEsc(detBonOpi.getTypeEsc());
		opi.setNbre(detBonOpi.getNbre());
		opi.setNumeroBon(numeroBon);
		return opi;
	}
}
