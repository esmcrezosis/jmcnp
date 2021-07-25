/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.services.obpsd;

import java.util.Date;
import java.util.List;

import com.esmc.mcnp.dto.bn.Nn;
import com.esmc.mcnp.model.ba.EuNn;
import com.esmc.mcnp.services.base.BaseService;

/**
 *
 * @author USER
 */
public interface EuNnService extends BaseService<EuNn, Integer> {

	public Long getLastInsertId();

	public Double getSumNnByTypeNn(String type_nn);

	public List<EuNn> findSourceNns(String type_nn);

	public List<EuNn> findNnByEmetteur(String codeMembre);

	public List<EuNn> findByMembre(String codeMembre);

	public String EmettreNn(Nn nn);

	public EuNn EmettreNn(String type_nn, Double montant, Date date);

	public EuNn EmettreNn(String type_nn, Double montant, String mode);
	
	public List<EuNn> getListNnByTypeNnAndCodeMembreAndTypeEmission(String type_nn, String codeMembre,  String typeEmission);
}
