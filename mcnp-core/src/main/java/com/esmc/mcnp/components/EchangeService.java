package com.esmc.mcnp.components;

import java.util.List;

import com.esmc.mcnp.dto.echange.Echange;
import com.esmc.mcnp.exception.CompteNonIntegreException;
import com.esmc.mcnp.exception.SoldeInsuffisantException;

public interface EchangeService {

	public String echangeNRNB(Echange echange);

	public boolean echangeGCP(String codeCompte, String typeEchange, double montant, String codeCompteObt,
			String codeMembre);

	public boolean echangeGCP(String codeMembre, String tegc, String typeEchange, double prk, double montant, String typeProduit)
			throws SoldeInsuffisantException, NullPointerException, CompteNonIntegreException;

	public boolean echangeGcp(String codeMembre, List<Long> tpagcps, String typeEchange, double prk, double montant, String typeProduit)
			throws SoldeInsuffisantException, NullPointerException, CompteNonIntegreException;

	public String echangeNRNN(Echange echange);

	public String echangeNRCNCS(Echange echange);
	
	public boolean echangeNRNB(String codeMembre, String numeroAppelOffre, double montant);

}
