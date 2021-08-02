/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.infrastructure.services.setting;

import com.esmc.mcnp.domain.entity.config.EuParametres;
import com.esmc.mcnp.domain.entity.config.EuParametresPK;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

/**
 *
 * @author USER
 */
public interface EuParametresService extends BaseService<EuParametres, EuParametresPK> {
	double getParametre(String codeParam, String libParam);

	int getParam(String codeParam, String libParam);

	String getStringParam(String code, String lib);
	
	Double getParamConso();
}
