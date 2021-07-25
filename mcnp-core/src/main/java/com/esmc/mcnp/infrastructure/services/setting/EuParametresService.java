/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.services.setting;

import com.esmc.mcnp.model.config.EuParametres;
import com.esmc.mcnp.model.config.EuParametresPK;
import com.esmc.mcnp.services.base.BaseService;

/**
 *
 * @author USER
 */
public interface EuParametresService extends BaseService<EuParametres, EuParametresPK> {
	public double getParametre(String codeParam, String libParam);

	public int getParam(String codeParam, String libParam);
	
	public Double getParamConso();
}
