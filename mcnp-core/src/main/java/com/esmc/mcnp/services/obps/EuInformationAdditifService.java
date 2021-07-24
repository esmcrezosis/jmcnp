/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.model.others.EuInformationAdditif;
import com.esmc.mcnp.services.base.BaseService;


/**
 *
 * @author Mawuli
 */
public interface EuInformationAdditifService extends BaseService<EuInformationAdditif, Long> {

	public List<EuInformationAdditif> retrouverInformationAd(String codemembre, String reference);
}
